package org.example.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
    private static Connection connection = null;
//    private static String url = "jdbc:mysql://localhost:3306/jdbc1";

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
