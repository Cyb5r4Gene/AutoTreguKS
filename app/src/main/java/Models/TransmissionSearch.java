package Models;

public class TransmissionSearch {
    boolean unspecified, manual, halfAutomatic, automatic;
    public TransmissionSearch(){}

    public TransmissionSearch(boolean unspecified, boolean manual, boolean halfAutomatic, boolean automatic) {
        this.unspecified = unspecified;
        this.manual = manual;
        this.halfAutomatic = halfAutomatic;
        this.automatic = automatic;
    }

    public boolean isUnspecified() {
        return unspecified;
    }

    public void setUnspecified(boolean unspecified) {
        this.unspecified = unspecified;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public boolean isHalfAutomatic() {
        return halfAutomatic;
    }

    public void setHalfAutomatic(boolean halfAutomatic) {
        this.halfAutomatic = halfAutomatic;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }
}
