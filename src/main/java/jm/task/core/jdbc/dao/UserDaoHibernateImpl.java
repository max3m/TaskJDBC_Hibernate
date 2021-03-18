package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.util.List;
import org.hibernate.*;

public class UserDaoHibernateImpl implements UserDao {
    private Session session = Util.getSessionFactory().openSession();;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS User" +
                "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(30) NOT NULL, " +
                "lastName VARCHAR(30) NOT NULL, " +
                "age TINYINT NOT NULL)").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = session.beginTransaction();
        String sqlCommand = "DROP TABLE IF EXISTS User";
        session.createSQLQuery(sqlCommand).executeUpdate();
        transaction.commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        transaction.commit();
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        transaction.commit();
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = session.beginTransaction();
        List<User> usersList = session.createQuery("FROM User").list();
        transaction.commit();
        return usersList;
    }
    @Override
    public void cleanUsersTable() {
        String sqlCommand = "DELETE FROM User";
        Transaction transaction = session.beginTransaction();
        session.createQuery(sqlCommand).executeUpdate();
        transaction.commit();
    }
}
