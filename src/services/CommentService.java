package services;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentService {
    private Database database;

    public CommentService(Database database) {
        this.database = database;
    }

    public void addComment(String username, String text) throws SQLException {
        String sql = "INSERT INTO comments (username, text) values ('" + username + "','" + text + "')";
        database.update(sql);
    }

    public List<String> getComment() throws SQLException {
        List<String> comments = new ArrayList<>();

        ResultSet resultSet = database.executeQuery("SELECT * FROM comments");

        while (resultSet.next()) {
            comments.add(resultSet.getString("text"));
        }

        return comments;
    }
}
