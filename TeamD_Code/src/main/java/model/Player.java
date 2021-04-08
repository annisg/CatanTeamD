package model;

import java.lang.reflect.Array;
import java.util.*;

import exception.*;
import gui.RemoveCardsGUI;

public class Player {

    private PlayerColor color;
    private Map<Resource, Integer> resources;
    private List<DevelopmentCard> developmentCards;
    private int settlementCount, cityCount, roadCount, knightCount;
    private RemoveCardsGUI removeCardsGUI;
    private TurnTracker turnTracker;
    private int numOfPlayersInEntireGame=0;
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

    public void discardResourceCard(Resource resource){
        resources.put(resource, resources.get(resource)-1);
    }

    public void addTracker(TurnTracker track){
        this.turnTracker = track;
        this.numOfPlayersInEntireGame = this.turnTracker.getNumPlayers();
    }

    public TurnTracker getTracker(){
        return this.turnTracker;
    }
    public int getPlayersInGame(){
        return this.turnTracker.getNumPlayers();
    }
    //adding the behavior to discard cards
    public  Map<Resource, Integer> getResourceCards(){
        return resources;
    }

    public ArrayList<Resource> giveResourceForTrading(Resource resource, int amount){
        if(amount>resources.get(resource)){
            throw new TooFewItemsException();
        }
        ArrayList<Resource> stuff = new ArrayList<Resource>();
        for(int i= 0; i<amount; i++){
            stuff.add(resource);
        }
        resources.put(resource, resources.get(resource)-amount);
        return stuff;
    }

    public void receiveResourceForTrading (ArrayList<Resource> stuff){
        resources.put(stuff.get(0), stuff.size() + resources.get(stuff.get(0)));
    }
    public void addResourec(Resource r, int amt){
        resources.put(r, amt);
    }

    public int getResourceCountString(String name) {

        Resource r = getResourceByName(name);
        if (r.equals(Resource.DESERT)) {
            throw new IllegalArgumentException();
        }


        return resources.get(r);
    }

    public void discardHalfResourceHand(){
        removeCardsGUI = new RemoveCardsGUI(this);

    }
    public int getResourceCount(Resource r) {

       // Resource r = getResourceByName(name);
        if (r.equals(Resource.DESERT)) {
            throw new IllegalArgumentException();
        }


        return resources.get(r);
    }

    public void giveCardsToPlayer(Player player){

    }
    public Resource getResourceByName(String name){
        if(name.equals("GRAIN")){
            return Resource.GRAIN;
        }
        else if(name.equals("BRICK")){
            return Resource.BRICK;
        }
        else if(name.equals("ORE")){
            return Resource.ORE;
        }
        else if(name.equals("LUMBER")){
            return Resource.LUMBER;
        }
        else if(name.equals("WOOL")){
            return Resource.WOOL;
        }
        else{
            return Resource.DESERT;
        }
    }

    //GRAIN, BRICK, ORE, LUMBER, WOOL, DESERT

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
