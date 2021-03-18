package gui;

public class GUIcomparisons {
    public boolean hexGUIequals(HexGUI hex1, HexGUI hex2) {
        return hex1.xCoord == hex2.xCoord &&
               hex1.yCoord == hex2.yCoord &&
               hex1.resourceToColor == hex2.resourceToColor;
    }

    public boolean hexNumGUIequals(HexNumGUI hexNum1, HexNumGUI hexNum2) {
        return hexNum1.xCoord == hexNum2.xCoord &&
               hexNum1.yCoord == hexNum2.yCoord &&
               hexNum1.hasOutline == hexNum2.hasOutline &&
               hexNum1.num == hexNum2.num;
    }

    public boolean playerGUISimilar(PlayerGUI player1, PlayerGUI player2) {
        return player1.playersPosition == player2.playersPosition &&
               player1.playerColor == player2.playerColor;
    }

    public boolean SpecialCardGUISimilar(SpecialCardGUI card1, SpecialCardGUI card2) {
        return card1.order == card2.order &&
               card1.holderColor.equals(card2.holderColor) &&
               card1.cardName.equals(card2.cardName);
    }
}
