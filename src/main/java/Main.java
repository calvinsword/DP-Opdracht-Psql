import data.AdresDAO;
import data.ReizigerDAO;
import domain.Adres;
import domain.Reiziger;
import infra.AdresDAOPsql;
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
    AdresDAOPsql adao = new AdresDAOPsql(connection);

    testReizigerDAO(rdao);
    testAdresDAO(rdao,adao);
//    testdelete(rdao,adao);
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
    private static void testAdresDAO(ReizigerDAO rdao, AdresDAO adao) throws Exception {
        System.out.println("\n---------- Test AdresDAO -------------");
        // Haal alle reizigers op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende Adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();
        //New adres
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        Adres placeholder = new Adres(6,"3432CD","9","beringerschans","nieuwegein",sietske);
        rdao.save(sietske);
        adao.save(placeholder);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");
        //UPDATE
        System.out.println("UPDATE + GETBYReiziger");
        System.out.println("Adres");
        System.out.println("Oude straat: " + placeholder.getStraat());
        placeholder.setStraat("Janssen");
        adao.update(placeholder);
        //GET
        Adres placeholder1 = adao.findByReiziger(sietske);
        System.out.println("Nieuwe straat: " + placeholder1.getStraat());
        System.out.print("\n[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.delete() ");

        System.out.println("*A2* reizigen koppelen aan adres");
        sietske.setAdres(placeholder);
        rdao.update(sietske);
        System.out.println("Adres van reiziger 77: " + adao.findByReiziger(sietske) + "\n");

        //DELETE
        System.out.println("[Test] AdresDAO.delete()");
        adao.delete(placeholder);
        rdao.delete(sietske);
        System.out.println(adressen.size() + " adressen\n");
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");
    }

    public static void testdelete(ReizigerDAO rdao,AdresDAO adao) throws Exception {
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        Adres placeholder = new Adres(6,"3432CD","9","beringerschans","nieuwegein",sietske);
        sietske.setAdres(null);
        rdao.update(sietske);
        adao.delete(placeholder);
        rdao.delete(sietske);

    }
}
