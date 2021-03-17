/*
package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.*;


import java.util.Properties;

public class Util {
    private final static String url = "jdbc:mysql://localhost:3306/JavaMentor?serverTimezone=Europe/Moscow&useSSL=false";
    private final static String username = "root";
    private final static String password = "5505Vfrc!";
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    static {
        try {
            Configuration configuration = getConfig();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(Registry);
        } catch (HibernateException he) {
            he.printStackTrace();
        }
    }

    public static Configuration getConfig() {

        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        properties.setProperty("hibernate.connection.url", url);
        properties.setProperty("hibernate.connection.username", username);
        properties.setProperty("hibernate.connection.password", password);
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        //properties.setProperty("hibernate.hbm2ddl.auto", "update");
        //properties.setProperty("hibernate.current_session_context_class", "thread");

        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(User.class);
        return configuration;
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

}

*/
package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class Util {

    private Util() {

    }

    public static SessionFactory getSessionFactory() {

        Configuration configuration = new Configuration();

        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("hibernate.connection.url",
                "jdbc:mysql://localhost:3306/JavaMentor?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        properties.setProperty("hibernate.connection.username", "root");
        properties.setProperty("hibernate.connection.password", "5505Vfrc!");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        configuration.setProperties(properties);
        configuration.addAnnotatedClass(User.class);

        return configuration.buildSessionFactory();
    }
}