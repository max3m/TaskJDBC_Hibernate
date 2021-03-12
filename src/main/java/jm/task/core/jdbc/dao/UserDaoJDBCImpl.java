package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection;

    String sqlCommand = null;
    Statement statement = null;

    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        sqlCommand = "CREATE TABLE IF NOT EXISTS Users " +
                "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(30) NOT NULL, " +
                "lastName VARCHAR(30) NOT NULL, " +
                "age TINYINT NOT NULL)";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException r) {
                r.printStackTrace();
            }

        }
    }

    public void dropUsersTable() {
        sqlCommand = "DROP TABLE IF EXISTS Users";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException r) {
                r.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        sqlCommand = "INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)";

        try {
            PreparedStatement pst = connection.prepareStatement(sqlCommand);
            pst.setString(1, name);
            pst.setString(2, lastName);
            pst.setInt(3, age);
            pst.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException r) {
                r.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        sqlCommand = "DELETE FROM Users WHERE id = ?";

        try {
            PreparedStatement pst = connection.prepareStatement(sqlCommand);
            pst.setLong(1, id);
            pst.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException r) {
                r.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        sqlCommand = "SELECT * FROM Users";
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
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException r) {
                r.printStackTrace();
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        sqlCommand = "TRUNCATE TABLE Users";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException r) {
                r.printStackTrace();
            }
        }
    }
}
