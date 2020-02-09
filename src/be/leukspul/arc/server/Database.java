package be.leukspul.arc.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public Database(String uri) throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:D:\\database.sql", "sa", "");
    }

    public void initialize() {
    }

    private Connection connection;
}
