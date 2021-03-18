package model;

import java.util.*;

public class LongestRoad {

    CandidateRoad currentLongestRoad;
    CandidateRoadFinder candidateRoadFinder;

    public LongestRoad(CandidateRoadFinder candidateRoadFinder) {
        this.currentLongestRoad = new CandidateRoad(PlayerColor.NONE, 0);
        this.candidateRoadFinder = candidateRoadFinder;
    }

    public LongestRoad(CandidateRoad currentLongestRoad, CandidateRoadFinder candidateRoadFinder) {
        this.currentLongestRoad = currentLongestRoad;
        this.candidateRoadFinder = candidateRoadFinder;
    }

    public PlayerColor getHolder() {
        return currentLongestRoad.color;
    }

    public int getLength() {
        return currentLongestRoad.length;
    }

    public void updateLongestRoad(GameMap board) {
        List<CandidateRoad> candidates = getCandidateRoads(board);
        currentLongestRoad = getNewLongestRoad(candidates);
    }

    List<CandidateRoad> getCandidateRoads(GameMap board) {

        EdgeMap edgeMap = board.getEdgeMap();
        List<CandidateRoad> allCandidates = getAllCandidateRoads(edgeMap);
        List<CandidateRoad> finalCandidates = new ArrayList<CandidateRoad>();

        PlayerColor[] colors = { PlayerColor.BLUE, PlayerColor.ORANGE, PlayerColor.RED, PlayerColor.WHITE };
        for (PlayerColor color : colors) {
            finalCandidates.add(getBestCandidateForColor(color, allCandidates));
        }

        return finalCandidates;
    }

    List<CandidateRoad> getAllCandidateRoads(EdgeMap edgeMap) {

        List<CandidateRoad> candidates = new ArrayList<CandidateRoad>();

        for (int i = 0; i < edgeMap.getNumberOfRows(); i++) {

            for (int j = 0; j < edgeMap.getNumberOfEdgesInRow(i); j++) {

                CandidateRoad candidateFromEdge = candidateRoadFinder
                        .getCandidateRoadFromEdge(edgeMap.getEdge(new MapPosition(i, j)));
                candidates.add(candidateFromEdge);

            }

        }

        return candidates;
    }

    CandidateRoad getBestCandidateForColor(PlayerColor color, List<CandidateRoad> candidateList) {

        int index = -1;
        int max = 0;

        for (int i = 0; i < candidateList.size(); i++) {
            if (candidateList.get(i).color == color && candidateList.get(i).length > max) {
                max = candidateList.get(i).length;
                index = i;
            }
        }

        if (index == -1) {
            return new CandidateRoad(color, 0);
        } else {
            return candidateList.get(index);
        }
    }

    CandidateRoad getNewLongestRoad(List<CandidateRoad> candidates) {

        int controllerRoadLength = getControllerRoadLength(candidates);
        List<CandidateRoad> validCandidates = getValidLongestRoads(candidates, controllerRoadLength);

        if (validCandidates.size() == 0) {
            return new CandidateRoad(PlayerColor.NONE, 0);
        } else if (validCandidates.size() == 1) {
            return validCandidates.get(0);
        } else {
            if (currentOwnerInTie(validCandidates)) {
                return new CandidateRoad(currentLongestRoad.color, controllerRoadLength);
            } else {
                return new CandidateRoad(PlayerColor.NONE, 0);
            }
        }
    }

    private boolean currentOwnerInTie(List<CandidateRoad> validCandidates) {

        for (CandidateRoad candidate : validCandidates) {
            if (candidate.color == currentLongestRoad.color) {
                return true;
            }
        }
        return false;
    }

    int getControllerRoadLength(List<CandidateRoad> candidates) {

        for (CandidateRoad candidate : candidates) {
            if (candidate.color == currentLongestRoad.color) {
                return candidate.length;
            }
        }

        return 0;

    }

    private List<CandidateRoad> getValidLongestRoads(List<CandidateRoad> candidates, int controllerRoadLength) {

        List<CandidateRoad> validCandidates = new ArrayList<CandidateRoad>();
        int max = 0;

        for (CandidateRoad candidate : candidates) {
            if (candidate.length >= 5 && candidate.length >= controllerRoadLength) {
                validCandidates.add(candidate);
                max = Math.max(max, candidate.length);
            }
        }

        Iterator<CandidateRoad> iterator = validCandidates.iterator();

        while (iterator.hasNext()) {
            CandidateRoad candidate = iterator.next();
            if (candidate.length != max) {
                iterator.remove();
            }
        }

        return validCandidates;
    }

}
