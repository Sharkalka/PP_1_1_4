package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/task_1_1_3";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "MySQL";

    private static Connection con = null;

    static {
        try {
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error SQL!");
        //} catch (ClassNotFoundException e) {
            //e.printStackTrace();
           // System.out.println("JDBC driver not found!");
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Cannot approve connection!");
        }
    }

    public static Connection getCon() {
        return con;
    }
}
