package Models;

public class ParkingSearch {

    boolean parkingSensors, camera, autonomeSystem;
    public ParkingSearch(){}

    public boolean isParkingSensors() {
        return parkingSensors;
    }

    public void setParkingSensors(boolean parkingSensors) {
        this.parkingSensors = parkingSensors;
    }

    public boolean isCamera() {
        return camera;
    }

    public void setCamera(boolean camera) {
        this.camera = camera;
    }

    public boolean isAutonomeSystem() {
        return autonomeSystem;
    }

    public void setAutonomeSystem(boolean autonomeSystem) {
        this.autonomeSystem = autonomeSystem;
    }

    public ParkingSearch(boolean parkingSensors, boolean camera, boolean autonomeSystem) {

        this.parkingSensors = parkingSensors;
        this.camera = camera;
        this.autonomeSystem = autonomeSystem;
    }
}
