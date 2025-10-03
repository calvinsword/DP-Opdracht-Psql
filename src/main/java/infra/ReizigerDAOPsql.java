package infra;

import data.AdresDAO;
import data.OVChipkaartDAO;
import data.ReizigerDAO;
import domain.Adres;
import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection con;
    AdresDAO adresDAO;
    OVChipkaartDAO ovChipkaartDAO;

    public ReizigerDAOPsql(Connection con, AdresDAO adresDAO, OVChipkaartDAO ovChipkaartDAO) {
        this.ovChipkaartDAO = ovChipkaartDAO;
        this.adresDAO = adresDAO;
        this.con = con;
    }

    public boolean save(Reiziger reiziger) throws Exception {
        PreparedStatement ps = con.prepareStatement("INSERT INTO reiziger(reiziger_id, voorletters,tussenvoegsel,achternaam,geboortedatum) VALUES (?,?,?,?,?)");
        ps.setInt(1, reiziger.getReiziger_id());
        ps.setString(2, reiziger.getVoorletters());
        ps.setString(3, reiziger.getTussenvoegsel());
        ps.setString(4, reiziger.getAchternaam());
        ps.setDate(5, reiziger.getGeboortedatum());
        int result = ps.executeUpdate();
        if (result > 0 && reiziger.getOvChipkaarten() != null) {
            for (OVChipkaart ov : reiziger.getOvChipkaarten()) {
                ov.setReiziger(reiziger);
                ovChipkaartDAO.save(ov);
            }
        }
        return result > 0;
    }

    public boolean update(Reiziger reiziger) throws SQLException {
        PreparedStatement ps = con.prepareStatement("UPDATE reiziger SET reiziger_id = ?, voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?");
        ps.setInt(1, reiziger.getReiziger_id());
        ps.setString(2, reiziger.getVoorletters());
        ps.setString(3, reiziger.getTussenvoegsel());
        ps.setString(4, reiziger.getAchternaam());
        ps.setDate(5, reiziger.getGeboortedatum());
        ps.setInt(6, reiziger.getReiziger_id());
        int result = ps.executeUpdate();
        if (result > 0 && reiziger.getAdres() != null) {
            adresDAO.update(reiziger.getAdres());
        }
        if (result > 0 && reiziger.getOvChipkaarten() != null) {
            for (OVChipkaart ov : reiziger.getOvChipkaarten()) {
                ov.setReiziger(reiziger);
                ovChipkaartDAO.save(ov);
            }
        }
        return result > 0;
    }

    public boolean delete(Reiziger reiziger) throws SQLException {
        if (reiziger.getOvChipkaarten() != null) {
            for (OVChipkaart ov : reiziger.getOvChipkaarten()) {
                ovChipkaartDAO.delete(ov);
            }
        }
        if (reiziger.getAdres() != null) {
            adresDAO.delete(reiziger.getAdres());
        }
        PreparedStatement ps = con.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?");
        ps.setInt(1, reiziger.getReiziger_id());
        ps.executeUpdate();
        return true;
    }

    public Reiziger findById(int id) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id = ?");
        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Reiziger r = new Reiziger(
                        rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum")
                );
                Adres a = adresDAO.findByReiziger(r);
                if (a != null) {
                    r.setAdres(a);
                }
                List<OVChipkaart> ovKaarten = ovChipkaartDAO.findByReiziger(r);
                r.setOvChipkaarten(ovKaarten);
                return r;
            }
        }
        throw new SQLException();
    }

    public List<Reiziger> findByGbdatum(Date date) throws SQLException {
        List<Reiziger> reizigers = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum = ?"))
        {
            pst.setDate(1, date);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reiziger r = new Reiziger(
                        rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum")
                );
                Adres a = adresDAO.findByReiziger(r);
                if (a != null) {
                    r.setAdres(a);
                }
                List<OVChipkaart> ovKaarten = ovChipkaartDAO.findByReiziger(r);
                r.setOvChipkaarten(ovKaarten);
                reizigers.add(r);
            }
        }
        return reizigers;
    }


    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> reizigers = new ArrayList<>();
        String query = "SELECT * FROM reiziger";
        try (PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Reiziger r = new Reiziger(
                        rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum")
                );
                Adres a = adresDAO.findByReiziger(r);
                if (a != null) {
                    r.setAdres(a);
                }
                List<OVChipkaart> ovKaarten = ovChipkaartDAO.findByReiziger(r);
                r.setOvChipkaarten(ovKaarten);
                reizigers.add(r);

            }
        }
        return reizigers;
    }
}

