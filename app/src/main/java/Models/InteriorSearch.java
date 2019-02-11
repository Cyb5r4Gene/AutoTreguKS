package Models;

public class InteriorSearch {
    boolean bluetooth, onBoardComputer, cdPlayer, electricWindow, electricSeats, heatedSeats, sportSeats,mp3, aux,
            steeringWheelButtons, nav, shiber, panoramic, roofRack, centralCloser;


    public InteriorSearch(){}
    public boolean isBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(boolean bluetooth) {
        this.bluetooth = bluetooth;
    }

    public boolean isOnBoardComputer() {
        return onBoardComputer;
    }

    public void setOnBoardComputer(boolean onBoardComputer) {
        this.onBoardComputer = onBoardComputer;
    }

    public boolean isCdPlayer() {
        return cdPlayer;
    }

    public void setCdPlayer(boolean cdPlayer) {
        this.cdPlayer = cdPlayer;
    }

    public boolean isElectricWindow() {
        return electricWindow;
    }

    public void setElectricWindow(boolean electricWindow) {
        this.electricWindow = electricWindow;
    }

    public boolean isElectricSeats() {
        return electricSeats;
    }

    public void setElectricSeats(boolean electricSeats) {
        this.electricSeats = electricSeats;
    }

    public boolean isHeatedSeats() {
        return heatedSeats;
    }

    public void setHeatedSeats(boolean heatedSeats) {
        this.heatedSeats = heatedSeats;
    }

    public boolean isSportSeats() {
        return sportSeats;
    }

    public void setSportSeats(boolean sportSeats) {
        this.sportSeats = sportSeats;
    }

    public boolean isMp3() {
        return mp3;
    }

    public void setMp3(boolean mp3) {
        this.mp3 = mp3;
    }

    public boolean isAux() {
        return aux;
    }

    public void setAux(boolean aux) {
        this.aux = aux;
    }

    public boolean isSteeringWheelButtons() {
        return steeringWheelButtons;
    }

    public void setSteeringWheelButtons(boolean steeringWheelButtons) {
        this.steeringWheelButtons = steeringWheelButtons;
    }

    public boolean isNav() {
        return nav;
    }

    public void setNav(boolean nav) {
        this.nav = nav;
    }

    public boolean isShiber() {
        return shiber;
    }

    public void setShiber(boolean shiber) {
        this.shiber = shiber;
    }

    public boolean isPanoramic() {
        return panoramic;
    }

    public void setPanoramic(boolean panoramic) {
        this.panoramic = panoramic;
    }

    public boolean isRoofRack() {
        return roofRack;
    }

    public void setRoofRack(boolean roofRack) {
        this.roofRack = roofRack;
    }

    public boolean isCentralCloser() {
        return centralCloser;
    }

    public void setCentralCloser(boolean centralCloser) {
        this.centralCloser = centralCloser;
    }

    public InteriorSearch(boolean bluetooth, boolean onBoardComputer, boolean cdPlayer, boolean electricWindow, boolean electricSeats, boolean heatedSeats, boolean sportSeats, boolean mp3, boolean aux, boolean steeringWheelButtons, boolean nav, boolean shiber, boolean panoramic, boolean roofRack, boolean centralCloser) {
        this.bluetooth = bluetooth;
        this.onBoardComputer = onBoardComputer;
        this.cdPlayer = cdPlayer;
        this.electricWindow = electricWindow;
        this.electricSeats = electricSeats;
        this.heatedSeats = heatedSeats;
        this.sportSeats = sportSeats;
        this.mp3 = mp3;
        this.aux = aux;
        this.steeringWheelButtons = steeringWheelButtons;
        this.nav = nav;
        this.shiber = shiber;
        this.panoramic = panoramic;
        this.roofRack = roofRack;
        this.centralCloser = centralCloser;
    }
}
