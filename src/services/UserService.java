package services;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private Database database;

    public UserService(Database database) {
        this.database = database;
    }

    public void register(String username, String password) throws SQLException {
        String sql = "INSERT INTO users1 (username, password) VALUES ('" + username + "', '" + password + "')";
        database.update(sql);
    }

    public boolean login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users1 WHERE username = '" + username + "' and password = '" + password + "'";
        ResultSet rs = database.executeQuery(sql);

        return rs.next();
    }
}
