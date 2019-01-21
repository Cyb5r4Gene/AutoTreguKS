package models;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Post {

    private int markaID,
            modeliID,
            karburantiID,
            variantiID,
            karroceriaID;

    private long cmimi;
    private boolean iNegociueshem;

    private String pronariID,
            titulli,
            nrDyer,
            regjistrimiPare,
            km,
            defekt,
            aksident,
            fuqia,
            transmisioni,
            targa,
            doganuar,
            regjistrim,
            nrUleseve,
            nrPronareve,
            ngjyra,
            materialiEnterier,
            ngjyraEnterier,
            pershkrimi,
            dataPostimit =  new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

    private Karakteristikat karakteristikat;

    public Post (){}

    public Post(int markaID, int modeliID, int karburantiID, int variantiID, int karroceriaID, long cmimi, boolean iNegociueshem,
                String pronariID, String titulli, String nrDyer, String regjistrimiPare, String km, String defekt, String aksident, String fuqia,
                String transmisioni, String targa, String doganuar, String regjistrim, String nrUleseve, String nrPronareve,
                String ngjyra, String materialiEnterier, String ngjyraEnterier, String pershkrimi, Karakteristikat karakteristikat) {
        this.markaID = markaID;
        this.modeliID = modeliID;
        this.karburantiID = karburantiID;
        this.variantiID = variantiID;
        this.karroceriaID = karroceriaID;
        this.cmimi = cmimi;
        this.iNegociueshem = iNegociueshem;
        this.pronariID = pronariID;
        this.nrDyer = nrDyer;
        this.regjistrimiPare = regjistrimiPare;
        this.km = km;
        this.defekt = defekt;
        this.aksident = aksident;
        this.fuqia = fuqia;
        this.transmisioni = transmisioni;
        this.targa = targa;
        this.doganuar = doganuar;
        this.regjistrim = regjistrim;
        this.nrUleseve = nrUleseve;
        this.nrPronareve = nrPronareve;
        this.ngjyra = ngjyra;
        this.materialiEnterier = materialiEnterier;
        this.ngjyraEnterier = ngjyraEnterier;
        this.pershkrimi = pershkrimi;
        this.karakteristikat = karakteristikat;
        this.titulli=titulli;
    }

    public String getTitulli() {
        return titulli;
    }

    public void setTitulli(String titulli) {
        this.titulli = titulli;
    }

    public String getDataPostimit() {
        return dataPostimit;
    }

    public void setDataPostimit(String dataPostimit) {
        this.dataPostimit = dataPostimit;
    }

    public String getPronariID() {
        return pronariID;
    }

    public void setPronariID(String pronariID) {
        this.pronariID = pronariID;
    }

    public Karakteristikat getKarakteristikat() {
        return karakteristikat;
    }

    public void setKarakteristikat(Karakteristikat karakteristikat) {
        this.karakteristikat = karakteristikat;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    public long getCmimi() {
        return cmimi;
    }

    public void setCmimi(long cmimi) {
        this.cmimi = cmimi;
    }

    public boolean isiNegociueshem() {
        return iNegociueshem;
    }

    public void setiNegociueshem(boolean iNegociueshem) {
        this.iNegociueshem = iNegociueshem;
    }

    public String getRegjistrim() {
        return regjistrim;
    }

    public void setRegjistrim(String regjistrim) {
        this.regjistrim = regjistrim;
    }

    public String getNrUleseve() {
        return nrUleseve;
    }

    public void setNrUleseve(String nrUleseve) {
        this.nrUleseve = nrUleseve;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public String getDoganuar() {
        return doganuar;
    }

    public void setDoganuar(String doganuar) {
        this.doganuar = doganuar;
    }

    public String getNrPronareve() {
        return nrPronareve;
    }

    public void setNrPronareve(String nrPronareve) {
        this.nrPronareve = nrPronareve;
    }

    public String getNgjyra() {
        return ngjyra;
    }

    public void setNgjyra(String ngjyra) {
        this.ngjyra = ngjyra;
    }

    public String getMaterialiEnterier() {
        return materialiEnterier;
    }

    public void setMaterialiEnterier(String materialiEnterier) {
        this.materialiEnterier = materialiEnterier;
    }

    public String getNgjyraEnterier() {
        return ngjyraEnterier;
    }

    public void setNgjyraEnterier(String ngjyraEnterier) {
        this.ngjyraEnterier = ngjyraEnterier;
    }

    public int getMarkaID() {
        return markaID;
    }

    public void setMarkaID(int markaID) {
        this.markaID = markaID;
    }

    public int getModeliID() {
        return modeliID;
    }

    public void setModeliID(int modeliID) {
        this.modeliID = modeliID;
    }

    public int getKarburantiID() {
        return karburantiID;
    }

    public void setKarburantiID(int karburantiID) {
        this.karburantiID = karburantiID;
    }

    public int getVariantiID() {
        return variantiID;
    }

    public void setVariantiID(int variantiID) {
        this.variantiID = variantiID;
    }

    public int getKarroceriaID() {
        return karroceriaID;
    }

    public void setKarroceriaID(int karroceriaID) {
        this.karroceriaID = karroceriaID;
    }

    public String getTransmisioni() {
        return transmisioni;
    }

    public void setTransmisioni(String transmisioni) {
        this.transmisioni = transmisioni;
    }

    public String getNrDyer() {
        return nrDyer;
    }

    public void setNrDyer(String nrDyer) {
        this.nrDyer = nrDyer;
    }

    public String getRegjistrimiPare() {
        return regjistrimiPare;
    }

    public void setRegjistrimiPare(String regjistrimiPare) {
        this.regjistrimiPare = regjistrimiPare;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getDefekt() {
        return defekt;
    }

    public void setDefekt(String defekt) {
        this.defekt = defekt;
    }

    public String getAksident() {
        return aksident;
    }

    public void setAksident(String aksident) {
        this.aksident = aksident;
    }

    public String getFuqia() {
        return fuqia;
    }

    public void setFuqia(String fuqia) {
        this.fuqia = fuqia;
    }





}
