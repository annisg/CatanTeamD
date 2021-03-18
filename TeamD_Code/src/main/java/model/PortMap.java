package model;

import java.util.*;

import exception.*;

public class PortMap {

    HashMap<MapPosition, Port> positionsToPorts;

    public PortMap() {
        positionsToPorts = new HashMap<MapPosition, Port>();

        ArrayList<Port> ports = portList();

        initializePortPositions(ports);
    }

    public PortMap(Random rand) {
        positionsToPorts = new HashMap<MapPosition, Port>();

        ArrayList<Port> ports = portList();
        Collections.shuffle(ports, rand);

        initializePortPositions(ports);
    }

    private ArrayList<Port> portList() {

        ArrayList<Port> ports = new ArrayList<Port>();

        ports.add(new GenericPort());
        ports.add(new GenericPort());
        ports.add(new GenericPort());
        ports.add(new GenericPort());
        ports.add(new SpecificPort(Resource.GRAIN));
        ports.add(new SpecificPort(Resource.LUMBER));
        ports.add(new SpecificPort(Resource.ORE));
        ports.add(new SpecificPort(Resource.WOOL));
        ports.add(new SpecificPort(Resource.BRICK));

        return ports;
    }

    private void initializePortPositions(ArrayList<Port> ports) {
        positionsToPorts.put(new MapPosition(10, 0), ports.get(0));
        positionsToPorts.put(new MapPosition(11, 0), ports.get(0));

        positionsToPorts.put(new MapPosition(1, 0), ports.get(1));
        positionsToPorts.put(new MapPosition(0, 0), ports.get(1));

        positionsToPorts.put(new MapPosition(0, 1), ports.get(2));
        positionsToPorts.put(new MapPosition(1, 2), ports.get(2));

        positionsToPorts.put(new MapPosition(6, 5), ports.get(3));
        positionsToPorts.put(new MapPosition(5, 5), ports.get(3));

        positionsToPorts.put(new MapPosition(11, 1), ports.get(4));
        positionsToPorts.put(new MapPosition(10, 2), ports.get(4));

        positionsToPorts.put(new MapPosition(8, 0), ports.get(5));
        positionsToPorts.put(new MapPosition(7, 0), ports.get(5));

        positionsToPorts.put(new MapPosition(9, 3), ports.get(6));
        positionsToPorts.put(new MapPosition(8, 4), ports.get(6));

        positionsToPorts.put(new MapPosition(2, 3), ports.get(7));
        positionsToPorts.put(new MapPosition(3, 4), ports.get(7));

        positionsToPorts.put(new MapPosition(4, 0), ports.get(8));
        positionsToPorts.put(new MapPosition(3, 0), ports.get(8));
    }

    public int getNumberOfPorts() {
        return 9;
    }

    public int getNumberOfPortIntersections() {
        return 2 * getNumberOfPorts();
    }

    Set<Port> getAllPorts() {
        Set<Port> ports = new HashSet<Port>();

        for (MapPosition currentPos : positionsToPorts.keySet()) {
            if (!ports.contains(positionsToPorts.get(currentPos))) {
                ports.add(positionsToPorts.get(currentPos));
            }
        }

        return ports;
    }

    public Port getPortFromPosition(MapPosition posToFind) {
        if (this.positionsToPorts.containsKey(posToFind)) {
            return this.positionsToPorts.get(posToFind);
        }
        throw new InvalidPortPositionException();
    }
}
