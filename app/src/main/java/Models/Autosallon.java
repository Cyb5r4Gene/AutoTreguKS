package Models;

public class Autosallon {
    public String  emri, pershkrimi, telefoni;

    public Autosallon() {
    }

    public Autosallon(String emriC, String pershkrimiC, String telefoniC) {
        emri = emriC;
        pershkrimi = pershkrimiC;
        telefoni = telefoniC;
    }

    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    public String getTelefoni() {
        return telefoni;
    }

    public void setTelefoni(String telefoni) {
        this.telefoni = telefoni;
    }
}
