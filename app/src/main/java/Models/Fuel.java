package Models;

public class Fuel {
    private boolean diesel, petrol, gas, electric, hybrid;

    public Fuel(){
        this.diesel=false;
        this.petrol=false;
        this.gas=false;
        this.electric=false;
        this.hybrid=false;
    }

    public Fuel(boolean diesel, boolean petrol, boolean gas, boolean electric, boolean hybrid) {
        this.diesel = diesel;
        this.petrol = petrol;
        this.gas = gas;
        this.electric = electric;
        this.hybrid = hybrid;
    }

    public boolean isDiesel() {
        return diesel;
    }

    public void setDiesel(boolean diesel) {
        this.diesel = diesel;
    }

    public boolean isPetrol() {
        return petrol;
    }

    public void setPetrol(boolean petrol) {
        this.petrol = petrol;
    }

    public boolean isGas() {
        return gas;
    }

    public void setGas(boolean gas) {
        this.gas = gas;
    }

    public boolean isElectric() {
        return electric;
    }

    public void setElectric(boolean electric) {
        this.electric = electric;
    }

    public boolean isHybrid() {
        return hybrid;
    }

    public void setHybrid(boolean hybrid) {
        this.hybrid = hybrid;
    }
}
