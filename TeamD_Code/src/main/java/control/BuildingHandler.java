package control;

import java.awt.*;
import java.text.MessageFormat;

import model.*;

public class BuildingHandler {

    private CatanGame catanGame;
    private PieceBuilder itemBuilder;
    private InputHandler handler;

    public BuildingHandler(CatanGame game, PieceBuilder builder, InputHandler handler) {
        this.catanGame = game;
        this.itemBuilder = builder;
        this.handler = handler;
    }

    void placeInitialSettlement(Point point) {
        Intersection desiredIntersection = this.catanGame.getGameMap().getClosestIntersectionToPoint(point);

        TurnTracker playerTracker = this.catanGame.getPlayerTracker();
        this.itemBuilder.buildInitialSettlement(playerTracker.getCurrentPlayer(), desiredIntersection);

        this.catanGame.justDrawProperty();
        this.catanGame.drawPlayers();
    }

    void placeInitialRoadAtClosestEdge(Point point) {
        Edge desiredEdge = this.catanGame.getGameMap().getClosestEdgeToPoint(point);

        TurnTracker playerTracker = this.catanGame.getPlayerTracker();
        this.itemBuilder.buildInitialRoad(playerTracker.getCurrentPlayer(), desiredEdge);
        playerTracker.passInitialTurn();
        this.catanGame.justDrawProperty();
        this.catanGame.drawPlayers();
        this.catanGame.advancedInitialPlacement();
    }

    void placeInitialSettlementRound2(Point point) {
        Intersection desiredIntersection = this.catanGame.getGameMap().getClosestIntersectionToPoint(point);

        TurnTracker playerTracker = this.catanGame.getPlayerTracker();
        this.itemBuilder.buildInitialSettlementRound2(playerTracker.getCurrentPlayer(), desiredIntersection);

        this.catanGame.justDrawProperty();
        this.catanGame.drawPlayers();
    }

    public void placeSettlement(Point point) {
        Intersection desiredIntersection = this.catanGame.getGameMap().getClosestIntersectionToPoint(point);

        TurnTracker playerTracker = this.catanGame.getPlayerTracker();
        this.itemBuilder.buildSettlement(playerTracker.getCurrentPlayer(), desiredIntersection);

        this.catanGame.justDrawProperty();
        this.catanGame.drawPlayers();
        this.catanGame.drawSpecialCards();
    }

    public boolean canPlaceSettlement(boolean hasNotRolled) {
        if (hasNotRolled) {
            handler.displayMessage(this.catanGame.getMessages().getString("BuildingHandler.0"));
            return false;
        }
        TurnTracker playerTracker = this.catanGame.getPlayerTracker();
        if (this.itemBuilder.hasResourcesToBuildSettlement(playerTracker.getCurrentPlayer())) {
            return true;
        }
        handler.displayMessage(this.catanGame.getMessages().getString("BuildingHandler.1"));
        return false;
    }

    public void placeCity(Point point) {
        Intersection desiredIntersection = this.catanGame.getGameMap().getClosestIntersectionToPoint(point);

        TurnTracker playerTracker = this.catanGame.getPlayerTracker();
        this.itemBuilder.buildCity(playerTracker.getCurrentPlayer(), desiredIntersection);

        this.catanGame.justDrawProperty();
        this.catanGame.drawPlayers();
    }

    public boolean canPlaceCity(boolean hasNotRolled) {
        if (hasNotRolled) {
            handler.displayMessage(this.catanGame.getMessages().getString("BuildingHandler.2"));
            return false;
        }
        TurnTracker playerTracker = this.catanGame.getPlayerTracker();
        if (this.itemBuilder.hasResourcesToBuildCity(playerTracker.getCurrentPlayer())) {
            return true;
        }
        handler.displayMessage(this.catanGame.getMessages().getString("BuildingHandler.3"));
        return false;
    }

    public void placeRoad(Point point, boolean chargeResources) {
        Edge desiredEdge = this.catanGame.getGameMap().getClosestEdgeToPoint(point);
    
        TurnTracker playerTracker = this.catanGame.getPlayerTracker();
        
        if(chargeResources) {
            this.itemBuilder.buildRoad(playerTracker.getCurrentPlayer(), desiredEdge);
        }
        else {
            this.itemBuilder.buildRoadWithCard(playerTracker.getCurrentPlayer(), desiredEdge);
        }

        this.catanGame.justDrawProperty();
        this.catanGame.drawPlayers();
        this.catanGame.drawSpecialCards();
    }

    public boolean canPlaceRoad(boolean hasNotRolled) {
        if (hasNotRolled) {
            handler.displayMessage(this.catanGame.getMessages().getString("BuildingHandler.4"));
            return false;
        }
        TurnTracker playerTracker = this.catanGame.getPlayerTracker();
        if (this.itemBuilder.hasResourcesToBuildRoad(playerTracker.getCurrentPlayer())) {
            return true;
        }
        handler.displayMessage(this.catanGame.getMessages().getString("BuildingHandler.5"));
        return false;
    }

    public void buyDevelopmentCard(boolean hasNotRolled) {
        if (canBuyDevCard(hasNotRolled)) {
            TurnTracker playerTracker = this.catanGame.getPlayerTracker();
            try {
                String drawnCard = this.itemBuilder.buildDevelopmentCard(playerTracker.getCurrentPlayer());
                handler.displayMessage(
                        MessageFormat.format(this.catanGame.getMessages().getString("BuildingHandler.6"), drawnCard));
                this.catanGame.drawPlayers();
            } catch (IllegalStateException e) {
                handler.displayMessage(this.catanGame.getMessages().getString("BuildingHandler.7"));
            }
        }
    }

    boolean canBuyDevCard(boolean hasNotRolled) {
        if (hasNotRolled) {
            handler.displayMessage(this.catanGame.getMessages().getString("BuildingHandler.8"));
            return false;
        }
        TurnTracker playerTracker = this.catanGame.getPlayerTracker();
        if (this.itemBuilder.hasResourcesToBuildDevelopmentCard(playerTracker.getCurrentPlayer())) {
            return true;
        }
        handler.displayMessage(this.catanGame.getMessages().getString("BuildingHandler.9"));
        return false;
    }
}
