package data;

import domain.OVChipkaart;
import domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    public boolean save(Product pr) throws SQLException;
    public boolean update(Product pr) throws SQLException;
    public boolean delete(Product pr) throws SQLException;
    public List<Product> findByOVChipkaart(OVChipkaart ov) throws SQLException;
    public List<Product> findAll() throws SQLException;
}
