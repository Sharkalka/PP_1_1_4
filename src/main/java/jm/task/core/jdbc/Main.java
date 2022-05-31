package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.createUsersTable();

        userService.saveUser("Aloha", "Alohov", (byte) 18);
        userService.saveUser("Boris", "Borisov", (byte) 12);
        userService.saveUser("Vanya", "Vanov", (byte) 42);
        userService.saveUser("Gregory", "Gregoryev", (byte) 54);

        for (User user : userService.getAllUsers()) {
            System.out.println(user.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
