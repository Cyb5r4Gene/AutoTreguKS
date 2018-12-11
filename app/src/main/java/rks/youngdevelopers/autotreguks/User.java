package rks.youngdevelopers.autotreguks;

import java.util.Date;

public class User {
    public String emri;
    public String mbiemri;
    String email;
    public String datelindja;
    public int qyteti;
    public String tel;
    int tipiLlogarise;
    public String dataKrijimit;
    public String editimiFundit;

    public User(String emri, String mbiemri, String email, String datelindja, int qyteti, String tel, int tipi, String dataKrijimit, String editimiFundit)
    {
        this.emri = emri;
        this.mbiemri = mbiemri;
        this.email = email;
        this.datelindja = datelindja;
        this.qyteti = qyteti;
        this.tel = tel;
        this.tipiLlogarise = tipi;
        this.dataKrijimit = dataKrijimit;
        this.editimiFundit = editimiFundit;
    }
}
