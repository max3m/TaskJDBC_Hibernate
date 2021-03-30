package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.util.List;
import org.hibernate.*;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getSessionFactory();
    private Session session;
    private Transaction tx;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS User" +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(30) NOT NULL, " +
                    "lastName VARCHAR(30) NOT NULL, " +
                    "age TINYINT NOT NULL)").executeUpdate();
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        session = sessionFactory.openSession();
        String sqlCommand = "DROP TABLE IF EXISTS User";
        try {
            tx = session.beginTransaction();
            session.createSQLQuery(sqlCommand).executeUpdate();
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        session = sessionFactory.openSession();
        List<User> usersList = session.createQuery("FROM User").list();
        session.close();
        return usersList;
    }
    @Override
    public void cleanUsersTable() {
        session = sessionFactory.openSession();
        String sqlCommand = "DELETE FROM User";
        try {
            tx = session.beginTransaction();
            session.createQuery(sqlCommand).executeUpdate();
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
