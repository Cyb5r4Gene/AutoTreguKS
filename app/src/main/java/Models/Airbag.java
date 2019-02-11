package Models;

public class Airbag {
    private boolean airbagShoferi, airbagAnesor, airbagIPrapme, airbagTjere;

    public Airbag(){}

    public Airbag(boolean airbagShoferi, boolean airbagAnesor, boolean airbagIPrapme, boolean airbagTjere) {
        this.airbagShoferi = airbagShoferi;
        this.airbagAnesor = airbagAnesor;
        this.airbagIPrapme = airbagIPrapme;
        this.airbagTjere = airbagTjere;
    }

    public boolean isAirbagShoferi() {
        return airbagShoferi;
    }

    public void setAirbagShoferi(boolean airbagShoferi) {
        this.airbagShoferi = airbagShoferi;
    }

    public boolean isAirbagAnesor() {
        return airbagAnesor;
    }

    public void setAirbagAnesor(boolean airbagAnesor) {
        this.airbagAnesor = airbagAnesor;
    }

    public boolean isAirbagIPrapme() {
        return airbagIPrapme;
    }

    public void setAirbagIPrapme(boolean airbagIPrapme) {
        this.airbagIPrapme = airbagIPrapme;
    }

    public boolean isAirbagTjere() {
        return airbagTjere;
    }

    public void setAirbagTjere(boolean airbagTjere) {
        this.airbagTjere = airbagTjere;
    }
}
