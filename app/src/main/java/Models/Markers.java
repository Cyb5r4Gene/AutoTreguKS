package Models;

public class Markers {
    String markerId, carDealerId;

    public Markers(String markerId, String carDealerId) {
        this.markerId = markerId;
        this.carDealerId = carDealerId;
    }

    public String getMarkerId() {
        return markerId;
    }

    public void setMarkerId(String markerId) {
        this.markerId = markerId;
    }

    public String getCarDealerId() {
        return carDealerId;
    }

    public void setCarDealerId(String carDealerId) {
        this.carDealerId = carDealerId;
    }
}
