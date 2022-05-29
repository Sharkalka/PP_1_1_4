package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();
        userDao.createUsersTable();

        userDao.saveUser("Aloha", "Alohov", (byte) 18);
        userDao.saveUser("Boris", "Borisov", (byte) 12);
        userDao.saveUser("Vanya", "Vanov", (byte) 42);
        userDao.saveUser("Gregory", "Gregoryev", (byte) 54);

        userDao.removeUserById(1);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
