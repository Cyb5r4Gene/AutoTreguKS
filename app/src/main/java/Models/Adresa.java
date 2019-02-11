package Models;

public class Adresa
{
    String rruga, nr, kodi, qyteti, lat, lng;

    public Adresa(){}

    public Adresa(String rruga, String nr, String kodi, String qyteti, String lat, String lng)
    {
        this.rruga = rruga;
        this.nr = nr;
        this.kodi = kodi;
        this.qyteti = qyteti;
        this.lat = lat;
        this.lng = lng;
    }

    public String getRruga() {
        return rruga;
    }

    public void setRruga(String rruga) {
        this.rruga = rruga;
    }

    public String getNr() {
        return nr;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getKodi() {
        return kodi;
    }

    public void setKodi(String kodi) {
        this.kodi = kodi;
    }

    public String getQyteti() {
        return qyteti;
    }

    public void setQyteti(String qyteti) {
        this.qyteti = qyteti;
    }
}
