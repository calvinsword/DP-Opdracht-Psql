package infra;

import data.ProductDAO;
import domain.OVChipkaart;
import domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProductDAOsql implements ProductDAO {
    Connection con = null;

    public ProductDAOsql(Connection con) {
        this.con = con;
    }

    public boolean save(Product product) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO product(product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?)"
        );
        ps.setInt(1, product.getProduct_nummer());
        ps.setString(2, product.getNaam());
        ps.setString(3, product.getBeschrijving());
        ps.setInt(4, product.getPrijs());
        ps.executeUpdate();

        // KOPPEL TABEL OV_CHIPKAART_PRODUCT
        if (product.getAlleOVChipkaarten() != null) {
            for (OVChipkaart ov : product.getAlleOVChipkaarten()) {
                PreparedStatement psLink = con.prepareStatement(
                        "INSERT INTO ov_chipkaart_product(kaart_nummer, product_nummer,status,last_update) VALUES (?, ?, ?, ?)"
                );
                psLink.setInt(1, ov.getId());
                psLink.setInt(2, product.getProduct_nummer());
                psLink.setString(3, "actief");
                java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
                psLink.setDate(4, date);
                psLink.executeUpdate();
            }
        }
        return true;
    }

    public boolean update(Product product) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?"
        );
        ps.setString(1, product.getNaam());
        ps.setString(2, product.getBeschrijving());
        ps.setInt(3, product.getPrijs());
        ps.setInt(4, product.getProduct_nummer());
        ps.executeUpdate();

        // OUDE KOPPEL TABEL VERWIJDEREN
        PreparedStatement psDel = con.prepareStatement(
                "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?"
        );
        psDel.setInt(1, product.getProduct_nummer());
        psDel.executeUpdate();

        // NIEUWE KOPPEL TABEL TOEVOEGEN
        if (product.getAlleOVChipkaarten() != null) {
            for (OVChipkaart ov : product.getAlleOVChipkaarten()) {
                PreparedStatement psLink = con.prepareStatement(
                        "INSERT INTO ov_chipkaart_product(kaart_nummer, product_nummer,status,last_update) VALUES (?, ?, ?, ?)"
                );
                psLink.setInt(1, ov.getId());
                psLink.setInt(2, product.getProduct_nummer());
                psLink.setString(3, "actief");
                java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
                psLink.setDate(4, date);
                psLink.executeUpdate();
            }
        }
        return true;
    }


    public boolean delete(Product product) throws SQLException {
        // EERST KOPPEL TABEL WEG
        PreparedStatement psDel = con.prepareStatement(
                "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?"
        );
        psDel.setInt(1, product.getProduct_nummer());
        psDel.executeUpdate();

        // PRODUCT VERWIJDEREN
        PreparedStatement ps = con.prepareStatement(
                "DELETE FROM product WHERE product_nummer = ?"
        );
        ps.setInt(1, product.getProduct_nummer());
        ps.executeUpdate();

        return true;
    }

    public List<Product> findByOVChipkaart(OVChipkaart ov) throws SQLException {
        List<Product> producten = new ArrayList<>();
        //GET PRODUCTNUMMER
        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM ov_chipkaart " +
                "INNER JOIN ov_chipkaart_product " +
                "ON ov_chipkaart.kaart_nummer = ov_chipkaart_product.kaart_nummer " +
                "WHERE ov_chipkaart.kaart_nummer = ?");

        ps.setInt(1,ov.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int productNummer = rs.getInt("product_nummer");

            try (PreparedStatement pst = con.prepareStatement("SELECT * FROM product WHERE product_nummer = ?")) {
                pst.setInt(1, productNummer);

                try(ResultSet rs1 = pst.executeQuery()) {
                    while (rs1.next()) {
                        Product p = new Product();
                        p.setProduct_nummer(rs1.getInt("product_nummer"));
                        p.setNaam(rs1.getString("naam"));
                        p.setBeschrijving(rs1.getString("beschrijving"));
                        p.setPrijs(rs1.getInt("prijs"));
                        producten.add(p);
                    }
                }
            }
        }
        rs.close();
        ps.close();

        return producten;
    }

    public List<Product> findAll() throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM product");
        ResultSet rs = ps.executeQuery();
        List<Product> producten = new ArrayList<>();
        while (rs.next()) {
            Product product = new Product(
                    rs.getInt("product_nummer"),
                    rs.getString("naam"),
                    rs.getString("beschrijving"),
                    rs.getInt("prijs"));
            producten.add(product);
        }
        return producten;
    }

}
