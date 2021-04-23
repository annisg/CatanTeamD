package control;

import gui.PortSelector;
import gui.ResourceSelector;
import model.Player;
import model.Resource;

import java.util.function.Function;

public class MaritimeTradeManager {
    public static final int DAFAULT_TRADE_COST = 4;
    public static final int GENERIC_TRADE_COST = 3;
    public static final int SPECIAL_TRADE_COST = 2;
    private final InputHandler inputHandler;
    private final CatanGame catanGame;
    Resource soldResource;
    Resource boughtResource;
    public Function<Object, Void> tradeViaPort = selected -> {
        tradeViaPort((PortSelector.PortTypes) selected);
        return null;
    };
    private Function<Object, Void> buyResource = selectedResource -> {
        buyResource((Resource) selectedResource);
        return null;
    };
    private Function<Object, Void> sellResource = selectedResource -> {
        sellResource((Resource) selectedResource);
        return null;
    };

    public MaritimeTradeManager(InputHandler inputHandler, CatanGame catanGame) {
        this.inputHandler = inputHandler;
        this.catanGame = catanGame;
    }

    public void trade() {
        if (inputHandler.hasNotRolled) {
            inputHandler.displayMessage("You must roll before trading.");
            return;
        }

        ResourceSelector sellingResourceSelector = new ResourceSelector(true, inputHandler);
        sellingResourceSelector.selectAndApply("Select the resource you would like to sell.", sellResource);
    }

    private void sellResource(Resource resource) {
        soldResource = resource;

        ResourceSelector buyingResourceSelector = new ResourceSelector(true, inputHandler);
        buyingResourceSelector.selectAndApply("Select the resource you would like to buy.", buyResource);
    }

    private void buyResource(Resource resource) {
        this.boughtResource = resource;

        PortSelector portSelector = new PortSelector(inputHandler);
        portSelector.selectAndApply("Select the port you would like to trade with.", tradeViaPort);
    }

    void tradeViaPort(PortSelector.PortTypes portType) {
        switch (portType) {
            case DEFAULT:
                defaultPortTrade();
                break;
            case GENERIC:
                genericPortTrade();
                break;
            case SPECIAL:
                specialPortTrade();
                break;
        }
    }


    private void defaultPortTrade() {
        exchangeResourcesWithBank(DAFAULT_TRADE_COST);
    }

    void genericPortTrade() {
        if (playerOwnsGenericHarbor()) {
            exchangeResourcesWithBank(GENERIC_TRADE_COST);
        } else {
            inputHandler.displayMessage("You do not own a Generic Harbor.");
        }
    }


    void specialPortTrade() {
        if (playerOwnsSpecialHarbor(soldResource)) {
            exchangeResourcesWithBank(SPECIAL_TRADE_COST);
        } else {
            inputHandler.displayMessage(String.format("You do not own a Special Harbor for %s", soldResource.toString()));
        }
    }

    boolean playerOwnsGenericHarbor() {
        return catanGame.doesCurrentPlayerOwnGenericHarbor();
    }

    private boolean playerOwnsSpecialHarbor(Resource resource) {
        return catanGame.doesCurrentPlayerOwnSpecialHarbor(resource);
    }

    private void exchangeResourcesWithBank(int tradeCost) {
        Player player = inputHandler.getCurrentPlayer();

        try {
            player.removeResource(this.soldResource, tradeCost);
        } catch (RuntimeException ignored) {
            inputHandler.displayMessage(String.format("Insufficient number of %s to trade (have %d, need %d)",
                    soldResource, player.getResourceCount(soldResource), tradeCost));
            return;
        }

        player.giveResource(boughtResource, 1);

        catanGame.drawPlayers();
    }
}
