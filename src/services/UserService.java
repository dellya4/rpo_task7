package services;

import database.Database;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private Database database;

    public UserService(Database database) {
        this.database = database;
    }

    public void register(String username, String password) throws SQLException {

        PreparedStatement stmt = database.prepare(
                "INSERT INTO users1 (username, password) VALUES (?, ?)"
        );

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        stmt.setString(1, username);
        stmt.setString(2, hashed);

        stmt.executeUpdate();
    }

    public boolean login(String username, String password) throws SQLException {

        PreparedStatement stmt = database.prepare(
                "SELECT * FROM users1 WHERE username = ?"
        );

        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String hash = rs.getString("password");
            return BCrypt.checkpw(password, hash);
        }

        return false;
    }
}
