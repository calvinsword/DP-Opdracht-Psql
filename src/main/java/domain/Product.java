package domain;

import java.util.List;

public class Product {

    private int product_nummer;

    private String naam;

    private String beschrijving;

    private int prijs;

    private List<OVChipkaart> alleOVChipkaarten;

    public Product(int Product_nummer, String Naam, String Beschrijving, int Prijs) {
        this.product_nummer = Product_nummer;
        this.naam = Naam;
        this.beschrijving = Beschrijving;
        this.prijs = Prijs;
    }

    public Product() {

    }

    public int getProduct_nummer() {
        return product_nummer;
    }
    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }
    public String getNaam() {
        return naam;
    }
    public void setNaam(String naam) {
        this.naam = naam;
    }
    public String getBeschrijving() {
        return beschrijving;
    }
    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }
    public int getPrijs() {
        return prijs;
    }
    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }
    public List<OVChipkaart> getAlleOVChipkaarten() {
        return alleOVChipkaarten;
    }
    public void addOVChipkaart(OVChipkaart ovChipkaart) {
        this.alleOVChipkaarten.add(ovChipkaart);
    }
    @Override
    public String toString() {
        return ("Product " + product_nummer + " met naam " + naam + " heeft beschrijving " + beschrijving + " en een prijs van " + prijs);
    }
}
