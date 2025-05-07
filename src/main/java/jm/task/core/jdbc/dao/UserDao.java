package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.io.Closeable;
import java.util.List;

public interface UserDao extends Closeable {
    void createUsersTable();

    String SQL_CREATE_TABLE = "CREATE TABLE users " +
            "(id BIGINT PRIMARY KEY AUTO_INCREMENT," +
            " name VARCHAR(255) NOT NULL," +
            " last_name VARCHAR(255) NOT NULL," +
            " age TINYINT UNSIGNED);";

    String SQL_DROP_TABLE = "DROP TABLE users;";

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();

    @Override
    void close();
}
