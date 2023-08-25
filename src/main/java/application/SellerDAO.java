package application;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public interface SellerDAO {
     void  create(Seller seller);
     void  update(Seller seller);
     void  deleteById(Seller seller);
     List<Seller> findByDepartment(Department department);
     Seller findById(Integer id);
     List<Seller> findAll();
}
