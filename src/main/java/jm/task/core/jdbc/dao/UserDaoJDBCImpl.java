package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection;

    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String sqlCommand = null;
    Statement statement = null;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        sqlCommand = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(30) NOT NULL, " +
                "lastName VARCHAR(30) NOT NULL, " +
                "age TINYINT NOT NULL)";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        sqlCommand = "DROP TABLE IF EXISTS users";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        sqlCommand = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        try {
            PreparedStatement pst = connection.prepareStatement(sqlCommand);
            pst.setString(1, name);
            pst.setString(2, lastName);
            pst.setInt(3, age);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        sqlCommand = "DELETE FROM users WHERE id = ?";

        try {
            PreparedStatement pst = connection.prepareStatement(sqlCommand);
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        sqlCommand = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlCommand);
            while(rs.next()){
                User user = new User(rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age"));
                user.setId(rs.getLong("id"));
                userList.add(user);
                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        dropUsersTable();
        createUsersTable();
    }
}
