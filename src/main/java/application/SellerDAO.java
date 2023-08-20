package application;

import model.entities.Seller;

public interface SellerDAO {
    public void  create(Seller seller);
    public void  update(Seller seller);
    public void  deleteById(Seller seller);
    public Seller findById(Integer id);
    public Seller findById();


}
