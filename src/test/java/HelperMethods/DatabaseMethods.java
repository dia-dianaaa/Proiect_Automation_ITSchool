package HelperMethods;

import SharedData.ConfigReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseMethods {

    private DatabaseMethods() {
    }

    public static Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
                ConfigReader.get("db.url"),
                ConfigReader.get("db.user"),
                ConfigReader.get("db.password"));
    }

    public static void executeUpdate(String sql) throws SQLException {
        try (Connection connection = openConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }
}
