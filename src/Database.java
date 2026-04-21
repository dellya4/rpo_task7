import java.sql.*;

public class Database {
    private Connection connection;

    public Database() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:test.db");
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public void update(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }
}
