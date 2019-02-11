package Models;

public class CarTypes {
    int marke, model;

    public CarTypes(){}

    public CarTypes(int marke, int model) {
        this.marke = marke;
        this.model = model;
    }

    public int getMarke() {
        return marke;
    }

    public void setMarke(int marke) {
        this.marke = marke;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }
}
