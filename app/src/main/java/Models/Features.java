package Models;

public class Features {
    private boolean bluetooth,
            onBoardKompjuter,
            CDPlayer, xhamaElektrik,
            uleseMeLevizjeElektrike,
            uleseMeNxemje,
            uleseSportive,
            mp3,
            aux,
            pullaNeTimon,
            navigacion,
            shiber,
            panorame,
            bagazhNeCati,
            mbylljeQendrore,
            pasqyreElektrike,
            amortizimSportiv,
            sportPakete,
            abs,
            katerX4,
            esp,
            dritaAdaptuese,
            sensorDritash,
            dritaTeMjegulles,
            dritaXenon,
            dritaBiXenon,
            sensorShiu,
            startStop;

    private int kondicioneri;
    private Airbag airbag;

    public Features(){}

    public Features(boolean bluetooth, boolean onBoardKompjuter, boolean CDPlayer, boolean xhamaElektrik,
                    boolean uleseMeLevizjeElektrike, boolean uleseMeNxemje, boolean uleseSportive, boolean mp3, boolean aux,
                    boolean pullaNeTimon, boolean navigacion, boolean shiber, boolean panorame, boolean bagazhNeCati,
                    boolean mbylljeQendrore, boolean pasqyreElektrike, boolean amortizimSportiv, boolean sportPakete,
                    boolean abs, boolean katerX4, boolean esp, boolean dritaAdaptuese, boolean sensorDritash,
                    boolean dritaTeMjegulles, boolean dritaXenon, boolean dritaBiXenon, boolean sensorShiu, boolean startStop,
                    int kondicioneri, Airbag airbag) {
        this.bluetooth = bluetooth;
        this.onBoardKompjuter = onBoardKompjuter;
        this.CDPlayer = CDPlayer;
        this.xhamaElektrik = xhamaElektrik;
        this.uleseMeLevizjeElektrike = uleseMeLevizjeElektrike;
        this.uleseMeNxemje = uleseMeNxemje;
        this.uleseSportive = uleseSportive;
        this.mp3 = mp3;
        this.aux = aux;
        this.pullaNeTimon = pullaNeTimon;
        this.navigacion = navigacion;
        this.shiber = shiber;
        this.panorame = panorame;
        this.bagazhNeCati = bagazhNeCati;
        this.mbylljeQendrore = mbylljeQendrore;
        this.pasqyreElektrike = pasqyreElektrike;
        this.amortizimSportiv = amortizimSportiv;
        this.sportPakete = sportPakete;
        this.abs = abs;
        this.katerX4 = katerX4;
        this.esp = esp;
        this.dritaAdaptuese = dritaAdaptuese;
        this.sensorDritash = sensorDritash;
        this.dritaTeMjegulles = dritaTeMjegulles;
        this.dritaXenon = dritaXenon;
        this.dritaBiXenon = dritaBiXenon;
        this.sensorShiu = sensorShiu;
        this.startStop = startStop;
        this.kondicioneri = kondicioneri;
        this.airbag = airbag;
    }

    public boolean isBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(boolean bluetooth) {
        this.bluetooth = bluetooth;
    }

    public boolean isOnBoardKompjuter() {
        return onBoardKompjuter;
    }

    public void setOnBoardKompjuter(boolean onBoardKompjuter) {
        this.onBoardKompjuter = onBoardKompjuter;
    }

    public boolean isCDPlayer() {
        return CDPlayer;
    }

    public void setCDPlayer(boolean CDPlayer) {
        this.CDPlayer = CDPlayer;
    }

    public boolean isXhamaElektrik() {
        return xhamaElektrik;
    }

    public void setXhamaElektrik(boolean xhamaElektrik) {
        this.xhamaElektrik = xhamaElektrik;
    }

    public boolean isUleseMeLevizjeElektrike() {
        return uleseMeLevizjeElektrike;
    }

    public void setUleseMeLevizjeElektrike(boolean uleseMeLevizjeElektrike) {
        this.uleseMeLevizjeElektrike = uleseMeLevizjeElektrike;
    }

    public boolean isUleseMeNxemje() {
        return uleseMeNxemje;
    }

    public void setUleseMeNxemje(boolean uleseMeNxemje) {
        this.uleseMeNxemje = uleseMeNxemje;
    }

    public boolean isUleseSportive() {
        return uleseSportive;
    }

    public void setUleseSportive(boolean uleseSportive) {
        this.uleseSportive = uleseSportive;
    }

    public boolean isMp3() {
        return mp3;
    }

    public void setMp3(boolean mp3) {
        this.mp3 = mp3;
    }

    public boolean isAux() {
        return aux;
    }

    public void setAux(boolean aux) {
        this.aux = aux;
    }

    public boolean isPullaNeTimon() {
        return pullaNeTimon;
    }

    public void setPullaNeTimon(boolean pullaNeTimon) {
        this.pullaNeTimon = pullaNeTimon;
    }

    public boolean isNavigacion() {
        return navigacion;
    }

    public void setNavigacion(boolean navigacion) {
        this.navigacion = navigacion;
    }

    public boolean isShiber() {
        return shiber;
    }

    public void setShiber(boolean shiber) {
        this.shiber = shiber;
    }

    public boolean isPanorame() {
        return panorame;
    }

    public void setPanorame(boolean panorame) {
        this.panorame = panorame;
    }

    public boolean isBagazhNeCati() {
        return bagazhNeCati;
    }

    public void setBagazhNeCati(boolean bagazhNeCati) {
        this.bagazhNeCati = bagazhNeCati;
    }

    public boolean isMbylljeQendrore() {
        return mbylljeQendrore;
    }

    public void setMbylljeQendrore(boolean mbylljeQendrore) {
        this.mbylljeQendrore = mbylljeQendrore;
    }

    public boolean isPasqyreElektrike() {
        return pasqyreElektrike;
    }

    public void setPasqyreElektrike(boolean pasqyreElektrike) {
        this.pasqyreElektrike = pasqyreElektrike;
    }

    public boolean isAmortizimSportiv() {
        return amortizimSportiv;
    }

    public void setAmortizimSportiv(boolean amortizimSportiv) {
        this.amortizimSportiv = amortizimSportiv;
    }

    public boolean isSportPakete() {
        return sportPakete;
    }

    public void setSportPakete(boolean sportPakete) {
        this.sportPakete = sportPakete;
    }

    public boolean isAbs() {
        return abs;
    }

    public void setAbs(boolean abs) {
        this.abs = abs;
    }

    public boolean isKaterX4() {
        return katerX4;
    }

    public void setKaterX4(boolean katerX4) {
        this.katerX4 = katerX4;
    }

    public boolean isEsp() {
        return esp;
    }

    public void setEsp(boolean esp) {
        this.esp = esp;
    }

    public boolean isDritaAdaptuese() {
        return dritaAdaptuese;
    }

    public void setDritaAdaptuese(boolean dritaAdaptuese) {
        this.dritaAdaptuese = dritaAdaptuese;
    }

    public boolean isSensorDritash() {
        return sensorDritash;
    }

    public void setSensorDritash(boolean sensorDritash) {
        this.sensorDritash = sensorDritash;
    }

    public boolean isDritaTeMjegulles() {
        return dritaTeMjegulles;
    }

    public void setDritaTeMjegulles(boolean dritaTeMjegulles) {
        this.dritaTeMjegulles = dritaTeMjegulles;
    }

    public boolean isDritaXenon() {
        return dritaXenon;
    }

    public void setDritaXenon(boolean dritaXenon) {
        this.dritaXenon = dritaXenon;
    }

    public boolean isDritaBiXenon() {
        return dritaBiXenon;
    }

    public void setDritaBiXenon(boolean dritaBiXenon) {
        this.dritaBiXenon = dritaBiXenon;
    }

    public boolean isSensorShiu() {
        return sensorShiu;
    }

    public void setSensorShiu(boolean sensorShiu) {
        this.sensorShiu = sensorShiu;
    }

    public boolean isStartStop() {
        return startStop;
    }

    public void setStartStop(boolean startStop) {
        this.startStop = startStop;
    }

    public int getKondicioneri() {
        return kondicioneri;
    }

    public void setKondicioneri(int kondicioneri) {
        this.kondicioneri = kondicioneri;
    }

    public Airbag getAirbag() {
        return airbag;
    }

    public void setAirbag(Airbag airbag) {
        this.airbag = airbag;
    }
}
