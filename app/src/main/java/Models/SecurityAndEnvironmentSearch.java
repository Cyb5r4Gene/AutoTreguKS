package Models;

public class SecurityAndEnvironmentSearch {
    boolean abs, fourX4, esp, adaptingLights, lightsSensor, xenonHeadlights, biXenonHeadlights, rainSensor, startStopSensor;
    public SecurityAndEnvironmentSearch (){}

    public boolean isAbs() {
        return abs;
    }

    public void setAbs(boolean abs) {
        this.abs = abs;
    }

    public boolean isFourX4() {
        return fourX4;
    }

    public void setFourX4(boolean fourX4) {
        this.fourX4 = fourX4;
    }

    public boolean isEsp() {
        return esp;
    }

    public void setEsp(boolean esp) {
        this.esp = esp;
    }

    public boolean isAdaptingLights() {
        return adaptingLights;
    }

    public void setAdaptingLights(boolean adaptingLights) {
        this.adaptingLights = adaptingLights;
    }

    public boolean isLightsSensor() {
        return lightsSensor;
    }

    public void setLightsSensor(boolean lightsSensor) {
        this.lightsSensor = lightsSensor;
    }

    public boolean isXenonHeadlights() {
        return xenonHeadlights;
    }

    public void setXenonHeadlights(boolean xenonHeadlights) {
        this.xenonHeadlights = xenonHeadlights;
    }

    public boolean isBiXenonHeadlights() {
        return biXenonHeadlights;
    }

    public void setBiXenonHeadlights(boolean biXenonHeadlights) {
        this.biXenonHeadlights = biXenonHeadlights;
    }

    public boolean isRainSensor() {
        return rainSensor;
    }

    public void setRainSensor(boolean rainSensor) {
        this.rainSensor = rainSensor;
    }

    public boolean isStartStopSensor() {
        return startStopSensor;
    }

    public void setStartStopSensor(boolean startStopSensor) {
        this.startStopSensor = startStopSensor;
    }

    public SecurityAndEnvironmentSearch(boolean abs, boolean fourX4, boolean esp, boolean adaptingLights, boolean lightsSensor, boolean xenonHeadlights, boolean biXenonHeadlights, boolean rainSensor, boolean startStopSensor) {

        this.abs = abs;
        this.fourX4 = fourX4;
        this.esp = esp;
        this.adaptingLights = adaptingLights;
        this.lightsSensor = lightsSensor;
        this.xenonHeadlights = xenonHeadlights;
        this.biXenonHeadlights = biXenonHeadlights;
        this.rainSensor = rainSensor;
        this.startStopSensor = startStopSensor;
    }
}
