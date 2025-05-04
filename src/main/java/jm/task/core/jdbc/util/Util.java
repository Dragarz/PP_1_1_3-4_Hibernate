package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/preproject";
    private static final String USER = "erd";
    private static final String PASSWORD = "erd";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Ошибка при подключении к базе данных или выполнении запроса");
            e.printStackTrace();
        }
        return null;
    }
    // реализуйте настройку соеденения с БД
}
