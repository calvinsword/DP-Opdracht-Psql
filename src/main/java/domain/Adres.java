package domain;

public class Adres {

    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaat;
    private Reiziger reiziger;

    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaat, Reiziger reiziger) {
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaat = woonplaat;
        this.reiziger = reiziger;
    }

    public Adres() {

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public String getHuisnummer() {
        return huisnummer;
    }
    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }
    public String getStraat() {
        return straat;
    }
    public void setStraat(String straat) {
        this.straat = straat;
    }
    public String getWoonplaat() {
        return woonplaat;
    }
    public void setWoonplaat(String woonplaat) {
        this.woonplaat = woonplaat;
    }
    public Reiziger getReiziger() {
        return reiziger;
    }

    @Override
    public String toString() {
        if (reiziger.getnaam() != null) {
            return ("Adres " + id + " met een postcode van " + postcode + " heeft huisnummer " + huisnummer + " straat " + straat + " woonplaat " + woonplaat + " reiziger " + reiziger.getnaam());
        }
        else return ("Adres " + id + " met een postcode van " + postcode + " heeft huisnummer " + huisnummer + " straat " + straat + " woonplaat " + woonplaat + " reiziger Null");
    }

}
