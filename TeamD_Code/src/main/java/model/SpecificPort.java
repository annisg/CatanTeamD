package model;

public class SpecificPort implements Port {

    Resource portResource;

    public SpecificPort(Resource specificResource) {
        if (specificResource == Resource.DESERT) {
            throw new IllegalArgumentException("Cannot have a desert port.");
        }
        this.portResource = specificResource;
    }

    @Override
    public int tradeRatioXto1ForResource(Resource resourceOffering) {
        if (resourceOffering == Resource.DESERT) {
            throw new IllegalArgumentException("Cannot trade desert cards");
        }
        if (this.portResource == resourceOffering) {
            return 2;
        }
        return 4;
    }

}
