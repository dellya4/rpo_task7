package services;

import database.Database;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private Database database;

    public UserService(Database database) {
        this.database = database;
    }

    public void register(String username, String password) throws SQLException {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        String sql = "INSERT INTO users1 (username, password) VALUES ('" + username + "', '" + hashed + "')";
        database.update(sql);
    }

    public boolean login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users1 WHERE username = '" + username + "'";
        ResultSet rs = database.executeQuery(sql);

        if (rs.next()) {
            String hash = rs.getString("password");
            return BCrypt.checkpw(password, hash);
        }

        return false;
    }
}
