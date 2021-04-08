package model;

import java.util.*;

import exception.*;

public class Player {

    private PlayerColor color;
    private Map<Resource, Integer> resources;
    private List<DevelopmentCard> developmentCards;
    private int settlementCount, cityCount, roadCount, knightCount;;

    public Player(PlayerColor color) {

        this.color = color;

        resources = new HashMap<Resource, Integer>();

        for (Resource resource : Resource.values()) {
            resources.put(resource, 0);
        }

        developmentCards = new ArrayList<DevelopmentCard>();

        settlementCount = 5;
        cityCount = 4;
        roadCount = 15;
        knightCount = 0;
    }

    public PlayerColor getColor() {
        return color;
    }
    
    public Set<Resource> getResourceTypes() {
        return resources.keySet();
    }

    public int getResourceCount(Resource resource) {

        if (resource.equals(Resource.DESERT)) {
            throw new IllegalArgumentException();
        }

        return resources.get(resource);
    }

    public int getResourceHandSize() {
        int sum = 0;

        for (Resource resource : Resource.values()) {
            sum += resources.get(resource);
        }

        return sum;
    }

    public void giveResource(Resource resource, int amount) {

        if (resource.equals(Resource.DESERT) || amount < 0) {
            throw new IllegalArgumentException();
        }

        int oldCount = resources.get(resource);
        resources.put(resource, oldCount + amount);
    }

    public void removeResource(Resource resource, int amount) {

        if (resource.equals(Resource.DESERT) || amount < 0) {
            throw new IllegalArgumentException();
        }

        int oldCount = resources.get(resource);

        if (oldCount - amount < 0) {
            throw new RuntimeException("Not enough cards to remove in hand.");
        }

        resources.put(resource, oldCount - amount);

    }

    public List<DevelopmentCard> getDevelopmentCards() {
        return developmentCards;
    }

    public void giveDevelopmentCard(DevelopmentCard card) {
        developmentCards.add(card);
    }

    public DevelopmentCard findDevelopmentCard(Class selectedDevelopmentCard) {
        List<DevelopmentCard> cards = this.getDevelopmentCards();
        for (DevelopmentCard card : cards) {
            if (card.canBePlayed() && card.getClass() == selectedDevelopmentCard) {
                return card;
            }
        }

        throw new ItemNotFoundException("Player does not have that type of card that is playable.");
    }

    public void letAllDevelopmentCardsBePlayed() {
        List<DevelopmentCard> cards = this.getDevelopmentCards();
        for (DevelopmentCard card : cards) {
            card.makePlayable();
        }
    }

    public void removeDevelopmentCard(DevelopmentCard card) {

        boolean didRemove = developmentCards.remove(card);

        if (didRemove) {
            return;
        } else {
            throw new ItemNotFoundException("Attempted to remove a development card that was not in hand.");
        }

    }

    public int getSettlementCount() {
        return settlementCount;
    }

    public void decrementSettlementCount() {
        settlementCount--;
    }

    public int getInitialSettlementCount() {
        return 5;
    }

    public int getCityCount() {
        return cityCount;
    }

    public int getInitialCityCount() {
        return 4;
    }

    public void decrementCityCount() {
        cityCount--;
    }

    public void incrementSettlementCount() {
        settlementCount++;
    }

    public int getRoadCount() {
        return roadCount;
    }

    public void decrementRoadCount() {
        roadCount--;
    }

    public int getNumKnights() {
        return knightCount;
    }

    public void incrementKnightCount() {
        knightCount++;
    }
}
