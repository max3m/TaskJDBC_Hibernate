package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    //private final static  String url = "jdbc:mysql://localhost/store?serverTimezone=Europe/Moscow&useSSL=false";
    private final static  String url = "jdbc:mysql://localhost:3306/JavaMentor?serverTimezone=Europe/Moscow&useSSL=false";
    private final static  String username = "root";
    private final static  String password = "5505Vfrc!";

    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        return DriverManager.getConnection(url, username, password);
    }
}

