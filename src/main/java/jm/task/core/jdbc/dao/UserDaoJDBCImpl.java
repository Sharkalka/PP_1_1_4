package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = Util.getCon().createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS User (" +
                    "userId BIGINT NOT NULL AUTO_INCREMENT, " +
                    "userName text NOT NULL, " +
                    "userLastName text NOT NULL, " +
                    "userAge smallint NOT NULL," +
                    "PRIMARY KEY (userId));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getCon().createStatement()){
            statement.execute("DROP TABLE IF EXISTS User;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getCon().prepareStatement(
                "INSERT INTO User(userName, userLastName, userAge) " +
                        "VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            if (preparedStatement.executeUpdate() > 0) {
                System.out.format("User with name %s %s wath saved in DB\n", name, lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement =
                     Util.getCon().prepareStatement("DELETE FROM User WHERE userId = ?")) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();

        try {
            Statement statement = Util.getCon().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM User");

            while(resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("userId"));
                user.setName(resultSet.getString("userName"));
                user.setLastName(resultSet.getString("userLastName"));
                user.setAge(resultSet.getByte("userAge"));

                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getCon().createStatement()) {
            statement.execute("TRUNCATE User;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
