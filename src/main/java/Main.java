import data.ReizigerDAO;
import domain.Reiziger;
import infra.ReizigerDAOPsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    static Connection connection = null;
    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            String url = "jdbc:postgresql://localhost:5432/OV-Chip?user=postgres&password=postgres";
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }
    private static void closeConnection() throws
            SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
    public static void main(String[] args) throws Exception {
    getConnection();
    ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
    testReizigerDAO(rdao);
    closeConnection();
    }
    private static void testReizigerDAO(ReizigerDAO rdao) throws Exception {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        //UPDATE
        System.out.println("UPDATE + GETBYID");
        System.out.println("Reiziger");
        System.out.println("Oude achternaam: " + sietske.getAchternaam());
        sietske.setAchternaam("Jansen");
        rdao.update(sietske);
        //GET
        Reiziger sietske1 = rdao.findById(77);
        System.out.println("Nieuwe achternaam: " + sietske1.getAchternaam());



        System.out.print("\n[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }
}
