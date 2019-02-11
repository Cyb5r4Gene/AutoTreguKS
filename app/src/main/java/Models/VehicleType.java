package Models;

public class VehicleType {
    boolean sedan, cabriolet, caravan, offroad, small;

    public VehicleType(){}

    public VehicleType(boolean sedan, boolean cabriolet, boolean caravan, boolean offroad, boolean small) {
        this.sedan = sedan;
        this.cabriolet = cabriolet;
        this.caravan = caravan;
        this.offroad = offroad;
        this.small = small;
    }

    public boolean isSedan() {
        return sedan;
    }

    public void setSedan(boolean sedan) {
        this.sedan = sedan;
    }

    public boolean isCabriolet() {
        return cabriolet;
    }

    public void setCabriolet(boolean cabriolet) {
        this.cabriolet = cabriolet;
    }

    public boolean isCaravan() {
        return caravan;
    }

    public void setCaravan(boolean caravan) {
        this.caravan = caravan;
    }

    public boolean isOffroad() {
        return offroad;
    }

    public void setOffroad(boolean offroad) {
        this.offroad = offroad;
    }

    public boolean isSmall() {
        return small;
    }

    public void setSmall(boolean small) {
        this.small = small;
    }
}
