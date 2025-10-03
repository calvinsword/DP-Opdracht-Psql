package domain;

import java.sql.Date;
import java.util.List;

public class OVChipkaart {

    private int id;
    private Date geldig_tot;
    private int klasse;
    private long saldo;
    private Reiziger reiziger;
    private List<Product> alleProducten = new java.util.ArrayList<>();

    public OVChipkaart(int id, Date geldig_tot, int klasse, long saldo, Reiziger reiziger) {
        this.id = id;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public OVChipkaart() {

    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getGeldig_tot() {
        return geldig_tot;
    }
    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }
    public int getKlasse() {
        return klasse;
    }
    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }
    public long getSaldo() {
        return saldo;
    }
    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }
    public Reiziger getReiziger() {
        return reiziger;
    }
    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }
    public List<Product> getAlleProducten() {
        return alleProducten;
    }
    public void addProduct(Product p) {
        if (!alleProducten.contains(p)) {
            alleProducten.add(p);
            p.addOVChipkaart(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder ovString = new StringBuilder();
        ovString.append("OVKaart " + id + " is geldig tot " + geldig_tot + " en met klasse " + klasse + " heeft een saldo van €" + saldo + " en in bezit van " + reiziger.getnaam());
        if (alleProducten != null && !alleProducten.isEmpty()) {
            ovString.append(" met producten: ");
            for (Product p : alleProducten) {
                ovString.append(p.getNaam()).append(" ");
            }
        } else {
            ovString.append(" met geen producten");
        }
        return ovString.toString();
    }
}
