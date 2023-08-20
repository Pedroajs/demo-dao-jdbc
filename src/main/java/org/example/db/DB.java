package org.example.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection connection = null;

    public static Connection getConnection() {
        if(connection == null){
            try{
                Properties properties = loadPropetties();
                String url = properties.getProperty("dburl");
                connection = DriverManager.getConnection(url,properties);
                System.out.println("Database is UP");
            } catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if(connection != null){
            try {
                connection.close();
                System.out.println("database is down");
            } catch (SQLException sqlException){
                throw new DbException(sqlException.getMessage());
            }
        }
    }
    public static void closeStatement(PreparedStatement preparedStatement){
        if(preparedStatement != null){
            try{
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet){
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException exception){
                throw new DbException(exception.getMessage());
            }
        }

    }
    private static Properties loadPropetties(){
        try(FileInputStream fileInputStream = new FileInputStream("C:\\Users\\55219\\Desktop\\dev\\demo-dao-jdbc\\src\\main\\resources\\db.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;

        }catch (IOException e){
            throw new DbException(e.getMessage());
        }

    }
}
