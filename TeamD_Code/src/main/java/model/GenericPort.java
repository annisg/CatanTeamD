package model;

public class GenericPort implements Port {

    @Override
    public int tradeRatioXto1ForResource(Resource resourceOffering) {
        if (resourceOffering == Resource.DESERT) {
            return Integer.MAX_VALUE;
        }
        return 3;
    }

}
