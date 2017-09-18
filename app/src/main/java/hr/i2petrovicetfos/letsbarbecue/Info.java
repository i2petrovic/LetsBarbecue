package hr.i2petrovicetfos.letsbarbecue;


public class Info {

    String infoID;
    String imeDogađaja;
    String datum;
    String vrijeme;
    String poruka;
    Double latitude;
    Double longitude;

    public Info() {

    }

    public Info(String infoID, String imeDogađaja, String datum, String vrijeme, String poruka, Double latitude, Double longitude) {
        this.infoID = infoID;
        this.imeDogađaja = imeDogađaja;
        this.datum = datum;
        this.vrijeme = vrijeme;
        this.poruka = poruka;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getInfoID() {
        return infoID;
    }

    public String getImeDogađaja() {
        return imeDogađaja;
    }

    public String getDatum() {
        return datum;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public String getPoruka() {
        return poruka;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}


