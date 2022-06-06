package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS User (" +
                    "userId BIGINT NOT NULL AUTO_INCREMENT, " +
                    "userName TEXT NOT NULL, " +
                    "userLastName TEXT NOT NULL, " +
                    "userAge TINYINT NOT NULL," +
                    "PRIMARY KEY (userId));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()){
            statement.execute("DROP TABLE IF EXISTS User;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(
                "INSERT INTO users(name, lastName, age) " +
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
                     Util.getConnection().prepareStatement("DELETE FROM users WHERE Id = ?")) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();

        try (Statement statement = Util.getConnection().createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

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
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute("TRUNCATE users;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
