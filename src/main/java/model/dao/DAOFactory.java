package model.dao;

import application.SellerDAO;
import org.example.db.DB;

public class DAOFactory {

    public static SellerDAO createSellerDAO(){
        return new SellerDAOJDBC(DB.getConnection());
    }
}
