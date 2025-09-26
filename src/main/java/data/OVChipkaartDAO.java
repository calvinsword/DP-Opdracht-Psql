package data;

import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    public boolean save(OVChipkaart ov) throws SQLException;
    public boolean update(OVChipkaart ov) throws SQLException;
    public boolean delete(OVChipkaart ov) throws SQLException;
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
    public List<OVChipkaart> findAll() throws SQLException;
}
