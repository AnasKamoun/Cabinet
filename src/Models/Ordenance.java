package Models;

import java.io.Serializable;

public class Ordenance implements Serializable {
    private int idOrd;
    private int idPat;
    private int idMe;
    private String date;

    // Constructor
    public Ordenance(int idOrd, int idPat, int idMe, String date) {
        this.idOrd = idOrd;
        this.idPat = idPat;
        this.idMe = idMe;
        this.date = date;
    }

    // Getters and setters
    public int getIdOrd() {
        return idOrd;
    }

    public void setIdOrd(int idOrd) {
        this.idOrd = idOrd;
    }

    public int getIdPat() {
        return idPat;
    }

    public void setIdPat(int idPat) {
        this.idPat = idPat;
    }

    public int getIdMe() {
        return idMe;
    }

    public void setIdMe(int idMe) {
        this.idMe = idMe;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
