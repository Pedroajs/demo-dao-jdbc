package model.dao;

import application.SellerDAO;
import model.entities.Department;
import model.entities.Seller;
import org.example.db.DB;
import org.example.db.DbException;

import java.sql.*;
import java.util.*;

public class SellerDAOJDBC implements SellerDAO {
    private Connection connection;

    public SellerDAOJDBC(Connection connection){
        this.connection = connection;
    }
    @Override
    public void create(Seller seller){
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        try{
//            DB.getConnection();
//            preparedStatement = connection.prepareStatement(
//                    "insert into seller (Name, Email, BirthDate, BaseSalary, DepartmentId) values (?,?,w,?,?)",
//                    Statement.RETURN_GENERATED_KEYS
//                    );
//            preparedStatement.setString(1, seller.getName());
//            preparedStatement.setString(2, seller.getEmail());
//            preparedStatement.setDate(3, (Date) seller.getBirthDate());
//            preparedStatement.setDouble(4, seller.getBaseSalary());
//            preparedStatement.setD(5, seller.getDepartment());
//
//            int rowsAffected = preparedStatement.executeUpdate();
//        }
//        catch (SQLException exception){
//            throw new DbException(exception.getMessage());
//        } finally {
//            DB.closeConnection();
//            DB.closeStatement(preparedStatement);
//        }
    }

    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Seller seller) {

    }

    @Override
    public List<Seller> findByDepartment(Department department){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("" +
                    "SELECT seller.*,department.Name as DepName" +
                    " FROM seller INNER JOIN department" +
                    " ON seller.DepartmentId = department.Id" +
                    " WHERE DepartmentId = ?" +
                    " ORDER BY Name");

            preparedStatement.setInt(1, department.getId());
            resultSet = preparedStatement.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (resultSet.next()){
                Department dep = map.get(resultSet.getInt("DepartmentId"));
                if(dep == null){
                    dep = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), dep);
                }
                Seller sel = instantiateSeller(resultSet, dep);
                list.add(sel);
            }
            return list;
        } catch (SQLException exception){
            exception.printStackTrace();
           throw new DbException(exception.getMessage());
        } finally {
          DB.closeStatement(preparedStatement);
          DB.closeResultSet(resultSet);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "+
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE seller.Id = ?"
            );

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Department dep = instantiateDepartment(resultSet);
                return instantiateSeller(resultSet, dep);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Seller> list = new ArrayList<>();
        Map<Integer, Department> map = new HashMap<>();
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "+
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id "
            );
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                Department dep =  map.get(resultSet.getInt("DepartmentId"));
                if(dep == null){
                    dep = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), dep);
                }
                Seller seller = instantiateSeller(resultSet, dep);
                list.add(seller);
            }
            return list;
        } catch (SQLException exception){
            exception.printStackTrace();
            throw  new DbException(exception.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
       Seller seller = new Seller();
        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setBirthDate(resultSet.getDate("BirthDate"));
        seller.setDepartment(department);
        return seller;
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException{
        Department dep = new Department();
        dep.setId(resultSet.getInt("DepartmentId"));
        dep.setName(resultSet.getString("DepName"));
        return dep;
    }
}
