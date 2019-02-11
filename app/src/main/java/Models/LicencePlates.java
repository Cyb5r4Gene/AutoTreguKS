package Models;

public class LicencePlates {
    boolean unspecified, kosovo, albania, foreign;
    public LicencePlates(){
        this.kosovo=false;
        this.albania=false;
        this.foreign=false;
        this.unspecified = false;
    }

    public LicencePlates(boolean unspecified, boolean kosovo, boolean albania, boolean foreign) {
        this.kosovo = kosovo;
        this.albania = albania;
        this.foreign = foreign;
        this.unspecified=unspecified;
    }

    public boolean isUnspecified() {
        return unspecified;
    }

    public void setUnspecified(boolean unspecified) {
        this.unspecified = unspecified;
    }

    public boolean isKosovo() {
        return kosovo;
    }

    public void setKosovo(boolean kosovo) {
        this.kosovo = kosovo;
    }

    public boolean isAlbania() {
        return albania;
    }

    public void setAlbania(boolean albania) {
        this.albania = albania;
    }

    public boolean isForeign() {
        return foreign;
    }

    public void setForeign(boolean foreign) {
        this.foreign = foreign;
    }
}
