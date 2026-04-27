package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import services.CommentService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CommentsHandler implements HttpHandler {

    private CommentService commentService;

    public CommentsHandler(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            List<String> comments = commentService.getComment();

            StringBuilder response = new StringBuilder();

            for (String c : comments) {
                response.append(Utils.escapeHtml(c)).append("\n");
            }

            Utils.send(exchange, response.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            Utils.send(exchange, "Error");
        }
    }
}