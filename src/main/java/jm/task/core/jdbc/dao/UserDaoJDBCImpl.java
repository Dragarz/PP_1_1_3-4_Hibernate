package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao, Closeable {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE users " +
                "(id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                " name VARCHAR(255) NOT NULL," +
                " last_name VARCHAR(255) NOT NULL," +
                " age TINYINT UNSIGNED);";
        this.sendSqlCommand(sql);
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE users;";
        this.sendSqlCommand(sql);
    }

    public void saveUser(String name, String lastName, byte age) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO users (name, last_name, age) VALUES(");
        sql.append("'").append(name).append("', ");
        sql.append("'").append(lastName).append("', ");
        sql.append(age).append(");");
        if(this.sendSqlCommand(sql.toString())) {
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        }
    }

    public void removeUserById(long id) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM users WHERE id = ");
        sql.append(id).append(";");
        this.sendSqlCommand(sql.toString());
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");
                users.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        this.sendSqlCommand(sql);
    }

    private boolean sendSqlCommand(String sql) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
