package jm.task.core.jdbc;

import java.io.IOException;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        try (UserServiceImpl userService = new UserServiceImpl()) {
            userService.createUsersTable();
            userService.saveUser("Fedor", "Fox", (byte) 22);
            userService.saveUser("Ivan", "Bad", (byte) 44);
            userService.saveUser("Alexandr", "Two", (byte) 32);
            userService.saveUser("Vasa", "Good", (byte) 35);
            System.out.println(userService.getAllUsers());
            userService.cleanUsersTable();
            userService.dropUsersTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
