package infra;

import data.ReizigerDAO;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection con;

    public ReizigerDAOPsql(Connection con) {
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
        return result > 0;
    }

    public boolean delete(Reiziger reiziger) throws SQLException {
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
                return new Reiziger(
                        rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum")
                );
            }
            else return  null;
        }

    }

    public List<Reiziger> findByGbdatum(Date date) throws SQLException {
        List<Reiziger> reizigers = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum = ?"))
        {
            pst.setDate(1, date);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reiziger reiziger = new Reiziger(
                        rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum")
                );
                reizigers.add(reiziger);
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
                Reiziger reiziger = new Reiziger(
                        rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum")
                );
                reizigers.add(reiziger);
            }
        }
        return reizigers;
    }
}

