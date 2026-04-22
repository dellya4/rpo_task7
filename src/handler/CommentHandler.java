package handler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import services.CommentService;
import services.SessionManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class CommentHandler implements HttpHandler {

    private CommentService commentService;
    private SessionManager sessionManager;

    public CommentHandler(CommentService commentService, SessionManager sessionManager) {
        this.commentService = commentService;
        this.sessionManager = sessionManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            Utils.send(exchange, "Only POST allowed");
            return;
        }

        String sessionId = Utils.getSessionId(exchange);
        String username = sessionManager.getUser(sessionId);

        if (username == null) {
            Utils.send(exchange, "Not authenticated");
            return;
        }

        Map<String, String> params = Utils.parseForm(exchange);
        String text = params.get("text");

        try {
            commentService.addComment(username, text);
            Utils.send(exchange, "Comment added");
        } catch (SQLException e) {
            e.printStackTrace();
            Utils.send(exchange, "Error");
        }
    }
}