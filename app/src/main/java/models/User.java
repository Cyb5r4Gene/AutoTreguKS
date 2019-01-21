package models;

public class User {
    public String emri;
    public String mbiemri;
    public String email;
    public String datelindja;
    public String tel;
    public String tipiLlogarise;
    public String dataKrijimit;
    public String editimiFundit;

    public User() { }

    public User(String emri, String mbiemri, String email, String datelindja,  String tel, String tipi, String dataKrijimit, String editimiFundit)
    {
        this.emri = emri;
        this.mbiemri = mbiemri;
        this.email = email;
        this.datelindja = datelindja;
        this.tel = tel;
        this.tipiLlogarise = tipi;
        this.dataKrijimit = dataKrijimit;
        this.editimiFundit = editimiFundit;
    }

    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    public String getMbiemri() {
        return mbiemri;
    }

    public void setMbiemri(String mbiemri) {
        this.mbiemri = mbiemri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDatelindja() {
        return datelindja;
    }

    public void setDatelindja(String datelindja) {
        this.datelindja = datelindja;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTipiLlogarise() {
        return tipiLlogarise;
    }

    public void setTipiLlogarise(String tipiLlogarise) {
        this.tipiLlogarise = tipiLlogarise;
    }

    public String getDataKrijimit() {
        return dataKrijimit;
    }

    public void setDataKrijimit(String dataKrijimit) {
        this.dataKrijimit = dataKrijimit;
    }

    public String getEditimiFundit() {
        return editimiFundit;
    }

    public void setEditimiFundit(String editimiFundit) {
        this.editimiFundit = editimiFundit;
    }
}
