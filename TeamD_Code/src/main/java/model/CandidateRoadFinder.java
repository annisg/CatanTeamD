package model;

import java.util.ArrayList;

public class CandidateRoadFinder {

    GameMap board;

    public CandidateRoadFinder(GameMap board) {
        this.board = board;
    }

    CandidateRoad getCandidateRoadFromEdge(Edge edge) {
        return edgeRecurse(edge, new ArrayList<Edge>(), new ArrayList<Intersection>());
    }

    CandidateRoad edgeRecurse(Edge edge, ArrayList<Edge> visitedEdges, ArrayList<Intersection> visitedIntersections) {

        if (edge.getRoadColor() == PlayerColor.NONE) {
            return new CandidateRoad(PlayerColor.NONE, 0);
        } else {

            int max = 0;
            ArrayList<Edge> newVisitedEdges = new ArrayList<Edge>(visitedEdges);
            newVisitedEdges.add(edge);

            for (Intersection intersection : board.getAllIntersectionsFromEdge(edge)) {
                if (!visitedIntersections.contains(intersection)) {
                    CandidateRoad candidate = intersectionRecurse(intersection, newVisitedEdges, visitedIntersections);
                    max = Math.max(max, candidate.length);
                }
            }

            return new CandidateRoad(edge.getRoadColor(), max + 1);
        }
    }

    private CandidateRoad intersectionRecurse(Intersection intersection, ArrayList<Edge> visitedEdges,
            ArrayList<Intersection> visitedIntersections) {

        PlayerColor originalColor = visitedEdges.get(0).getRoadColor();

        if (intersectionNotBlocked(intersection, originalColor)) {
            return new CandidateRoad(originalColor, 0);
        } else {

            int max = 0;
            ArrayList<Intersection> newVisitedIntersections = new ArrayList<Intersection>(visitedIntersections);
            newVisitedIntersections.add(intersection);

            for (Edge edge : board.getAllEdgesFromIntersection(intersection)) {

                if (edgeIsVisitable(edge, visitedEdges, originalColor)) {
                    CandidateRoad candidate = edgeRecurse(edge, visitedEdges, newVisitedIntersections);
                    max = Math.max(max, candidate.length);
                }
            }

            return new CandidateRoad(originalColor, max);
        }
    }

    private boolean edgeIsVisitable(Edge edge, ArrayList<Edge> visitedEdges, PlayerColor originalColor) {
        return edge.getRoadColor() == originalColor && !visitedEdges.contains(edge);
    }

    private boolean intersectionNotBlocked(Intersection intersection, PlayerColor originalColor) {
        return (intersection.hasSettlement() || intersection.hasCity())
                && intersection.getBuildingColor() != originalColor;
    }
}