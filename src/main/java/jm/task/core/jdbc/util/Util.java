package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/preproject";
    private static final String USER = "erd";
    private static final String PASSWORD = "erd";

    private static final SessionFactory sessionFactory = buildHibernateConnection();
    private static final Connection connection = buildJDBCConnection();

    public static Connection buildJDBCConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Ошибка при подключении к базе данных или выполнении запроса");
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getJDBCConnection() {
        return connection;
    }

    public static SessionFactory getHibernateConnection() {
        return sessionFactory;
    }

    public static void closeConnection() {
        try {
            assert connection != null;
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
