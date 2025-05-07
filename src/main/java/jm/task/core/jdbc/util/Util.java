package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/preproject";
    private static final String USER = "erd";
    private static final String PASSWORD = "erd";

    private static final SessionFactory sessionFactory = buildHibernateConnection();

    public static SessionFactory getHibernateConnection() {
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        getHibernateConnection().close();
    }

    public static SessionFactory buildHibernateConnection() {
        try {
            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", DB_URL);
            configuration.setProperty("hibernate.connection.username", USER);
            configuration.setProperty("hibernate.connection.password", PASSWORD);
            configuration.setProperty("hibernate.hbm2ddl.auto", "none");

            configuration.addAnnotatedClass(User.class);

            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    // реализуйте настройку соеденения с БД
}
