package domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> ovChipkaarten =  new ArrayList<>();


    public Reiziger(int Reiziger_id, String Voorletters, String Tussenvoegsel, String Achternaam, Date Gebortedatum) {
        this.reiziger_id = Reiziger_id;
        this.voorletters = Voorletters;
        this.tussenvoegsel = Tussenvoegsel;
        this.achternaam = Achternaam;
        this.geboortedatum = Gebortedatum;
    }

    public Reiziger() {

    }

    public int getReiziger_id() {
        return reiziger_id;
    }
    public void setReiziger_id(int Reiziger_id) {
        this.reiziger_id = Reiziger_id;
    }
    public String getVoorletters() {
        return voorletters;
    }
    public void setVoorletters(String Voorletters) {
        this.voorletters = Voorletters;
    }
    public String getTussenvoegsel() {
        return tussenvoegsel;
    }
    public void setTussenvoegsel(String Tussenvoegsel) {
        this.tussenvoegsel = Tussenvoegsel;
    }
    public String getAchternaam() {
        return achternaam;
    }
    public void setAchternaam(String Achternaam) {
        this.achternaam = Achternaam;
    }
    public Date getGeboortedatum() {
        return geboortedatum;
    }
    public void setGeboortedatum(Date Gebortedatum) {
        this.geboortedatum = Gebortedatum;
    }
    public String getnaam(){
        if (tussenvoegsel != null){
            return(voorletters + ". " + tussenvoegsel + ". " + achternaam);
        }
        else {
            return(voorletters + ". " + achternaam);
        }
    }
    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setOvChipkaarten(List<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    public boolean voegToeOVChipkaart(OVChipkaart ovChipkaart) {
        if (ovChipkaart != null && !ovChipkaarten.contains(ovChipkaart)) {
            ovChipkaarten.add(ovChipkaart);
            ovChipkaart.setReiziger(this);
            return true;
        }
        return false;
    }

    public boolean verwijderOVChipkaart(OVChipkaart ovChipkaart) {
        if (ovChipkaart != null && ovChipkaarten.contains(ovChipkaart)) {
            ovChipkaarten.remove(ovChipkaart);
            ovChipkaart.setReiziger(null);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(voorletters + ", " + tussenvoegsel + ", " + achternaam + " heeft een reiziger id van: " + reiziger_id + " en een geboorte datum van: " + geboortedatum + "." + " adress{" + adres + "}" + " ovChipkaarten{" + ovChipkaarten + "}");
        if (adres != null){
            str.append(", " + adres);
        }
        if (ovChipkaarten != null && !ovChipkaarten.isEmpty()) {
            str.append(", OVChipkaarten: ");
            for (OVChipkaart ovChipkaart : ovChipkaarten) {
                str.append(ovChipkaart.toString()).append("; ");
            }
        }
        return str.toString();
    }
}