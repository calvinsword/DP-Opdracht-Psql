package infra;

import data.OVChipkaartDAO;
import domain.OVChipkaart;
import domain.Product;
import domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOsql implements OVChipkaartDAO {
    Connection con = null;

    public OVChipkaartDAOsql(Connection con) {
        this.con = con;
    }

    public boolean save(OVChipkaart ov) throws SQLException {
        // Eerst de OVChipkaart zelf
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO ov_chipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?)"
        );
        ps.setInt(1, ov.getId());
        ps.setDate(2, ov.getGeldig_tot());
        ps.setInt(3, ov.getKlasse());
        ps.setLong(4, ov.getSaldo());
        ps.setInt(5, ov.getReiziger().getReiziger_id());
        ps.executeUpdate();

        //KOPPEL TABEL OV_CHIPKAART_PRODUCT
        if (ov.getAlleProdcuten() != null) {
            for (Product p : ov.getAlleProdcuten()) {
                PreparedStatement psLink = con.prepareStatement(
                        "INSERT INTO ov_chipkaart_product(kaart_nummer, product_nummer,status,last_update) VALUES (?, ?, ?, ?)"
                );
                psLink.setInt(1, ov.getId());
                psLink.setInt(2, p.getProduct_nummer());
                psLink.setString(3, "actief");
                psLink.setDate(4, new java.sql.Date(System.currentTimeMillis()));
                psLink.executeUpdate();
            }
        }

        return true;
    }

    public boolean update(OVChipkaart ov) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?"
        );
        ps.setDate(1, ov.getGeldig_tot());
        ps.setInt(2, ov.getKlasse());
        ps.setLong(3, ov.getSaldo());
        ps.setInt(4, ov.getReiziger().getReiziger_id());
        ps.setInt(5, ov.getId());
        ps.executeUpdate();

    //OUDE KOPPELINGEN VERWIJDEREN
        PreparedStatement psDel = con.prepareStatement(
                "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?"
        );
        psDel.setInt(1, ov.getId());
        psDel.executeUpdate();

        //NIEUWE KOPPELINGEN TOEVOEGEN
        if (ov.getAlleProdcuten() != null) {
            for (Product p : ov.getAlleProdcuten()) {
                PreparedStatement psLink = con.prepareStatement(
                        "INSERT INTO ov_chipkaart_product(kaart_nummer, product_nummer,status,last_update) VALUES (?, ?, ?, ?)"
                );
                psLink.setInt(1, ov.getId());
                psLink.setInt(2, p.getProduct_nummer());
                psLink.setString(3, "actief");
                psLink.setDate(4, new java.sql.Date(System.currentTimeMillis()));
                psLink.executeUpdate();
            }
        }

        return true;
    }

    public boolean delete(OVChipkaart ov) throws SQLException {
        // EEST KOPPEL TABEL WEG VOOR ERRORS VOORKOMEN
        PreparedStatement psDel = con.prepareStatement(
                "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?"
        );
        psDel.setInt(1, ov.getId());
        psDel.executeUpdate();

        // OVKAART VERWIJDEREN
        PreparedStatement ps = con.prepareStatement(
                "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?"
        );
        ps.setInt(1, ov.getId());
        ps.executeUpdate();

        return true;
    }

    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        List<OVChipkaart> allOVChipkaarten = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM ov_chipkaart INNER JOIN reiziger ON reiziger.reiziger_id = ov_chipkaart.reiziger_id WHERE ? = reiziger.reiziger_id");
        ps.setInt(1, reiziger.getReiziger_id());
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                OVChipkaart ov = new OVChipkaart(
                        rs.getInt("kaart_nummer"),
                        rs.getDate("geldig_tot"),
                        rs.getInt("klasse"),
                        rs.getLong("saldo"),
                        new Reiziger(rs.getInt("reiziger_id"), rs.getString("voorletters"),rs.getString("tussenvoegsel"),rs.getString("achternaam"),rs.getDate("geboortedatum"))
                );
                allOVChipkaarten.add(ov);
            }
        }
        return  allOVChipkaarten;
    }

    public List<OVChipkaart> findAll() throws SQLException {
        List<OVChipkaart> allOVChipkaarten = new ArrayList<>();
        String query = "SELECT * FROM ov_chipkaart INNER JOIN reiziger ON reiziger.reiziger_id = ov_chipkaart.reiziger_id";
        try (PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                OVChipkaart ov = new OVChipkaart(
                        rs.getInt("kaart_nummer"),
                        rs.getDate("geldig_tot"),
                        rs.getInt("klasse"),
                        rs.getLong("saldo"),
                        new Reiziger(rs.getInt("reiziger_id"), rs.getString("voorletters"),rs.getString("tussenvoegsel"),rs.getString("achternaam"),rs.getDate("geboortedatum"))
                );
                allOVChipkaarten.add(ov);
            }
        }
        return allOVChipkaarten;
    }
}
