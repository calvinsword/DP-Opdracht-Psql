package data;

import domain.Reiziger;

import java.sql.Date;
import java.util.List;

public interface ReizigerDAO {
    public boolean save(Reiziger reiziger) throws Exception;
    public boolean update(Reiziger reiziger) throws Exception;
    public boolean delete(Reiziger reiziger) throws Exception;
    public Reiziger findById(int id) throws Exception;
    public List<Reiziger> findByGbdatum(Date date) throws Exception;
    public List<Reiziger> findAll() throws Exception;
}
