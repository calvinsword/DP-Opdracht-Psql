package infra;

import data.AdresDAO;
import domain.Adres;
import domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    Connection con = null;

    public AdresDAOPsql(Connection conn){
        this.con = conn;
    }
    public boolean save(Adres adres) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO adres(adres_id, postcode,huisnummer,straat,woonplaats,reiziger_id) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, adres.getId());
        ps.setString(2, adres.getPostcode());
        ps.setString(3, adres.getHuisnummer());
        ps.setString(4, adres.getStraat());
        ps.setString(5, adres.getWoonplaat());
        ps.setInt(6, adres.getReiziger().getReiziger_id());
        int result = ps.executeUpdate();
        return result > 0;
    }
    public boolean update(Adres adres) throws SQLException {
        PreparedStatement ps = con.prepareStatement("UPDATE adres SET adres_id = ?, postcode = ?,huisnummer = ?,straat = ?,woonplaats = ?,reiziger_id = ? WHERE adres_id=?");
        ps.setInt(1, adres.getId());
        ps.setString(2, adres.getPostcode());
        ps.setString(3, adres.getHuisnummer());
        ps.setString(4, adres.getStraat());
        ps.setString(5, adres.getWoonplaat());
        ps.setInt(6, adres.getReiziger().getReiziger_id());
        ps.setInt(7, adres.getId());
        int result = ps.executeUpdate();
        return result > 0;
    }
    public boolean delete(Adres adres) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM adres WHERE adres_id = ?");
        ps.setInt(1, adres.getId());
        ps.executeUpdate();
        return true;
    }
    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM adres INNER JOIN reiziger ON reiziger.reiziger_id = adres.reiziger_id WHERE ? = reiziger.reiziger_id");
        ps.setInt(1, reiziger.getReiziger_id());
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Adres(
                        rs.getInt("adres_id"),
                        rs.getString("postcode"),
                        rs.getString("huisnummer"),
                        rs.getString("straat"),
                        rs.getString("woonplaats"),
                        new Reiziger(rs.getInt("reiziger_id"), rs.getString("voorletters"),rs.getString("tussenvoegsel"),rs.getString("achternaam"),rs.getDate("geboortedatum"))
                );
            }
            else return  null;
        }
    }
    public List<Adres> findAll() throws SQLException {
        List<Adres> allAddressen = new ArrayList<>();
        String query = "SELECT * FROM adres INNER JOIN reiziger ON reiziger.reiziger_id = adres.reiziger_id";
        try (PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Adres adres = new Adres(
                        rs.getInt("adres_id"),
                        rs.getString("postcode"),
                        rs.getString("huisnummer"),
                        rs.getString("straat"),
                        rs.getString("woonplaats"),
                        new Reiziger(rs.getInt("reiziger_id"), rs.getString("voorletters"),rs.getString("tussenvoegsel"),rs.getString("achternaam"),rs.getDate("geboortedatum"))
                );
                allAddressen.add(adres);
            }
        }
        return allAddressen;
    }
}
