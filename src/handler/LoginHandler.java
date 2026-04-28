package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import services.SessionManager;
import services.UserService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class LoginHandler implements HttpHandler {

    private UserService userService;
    private SessionManager sessionManager;

    public LoginHandler(UserService userService, SessionManager sessionManager) {
        this.userService = userService;
        this.sessionManager = sessionManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            Utils.send(exchange, "Only POST allowed");
            return;
        }

        Map<String, String> params = Utils.parseForm(exchange);

        String username = params.get("username");
        String password = params.get("password");

        try {
            if (userService.login(username, password)) {
                String sessionId = sessionManager.createSession(username);

                exchange.getResponseHeaders().add(
                        "Set-Cookie",
                        "sessionId=" + sessionId + "; HttpOnly"
                );

                Utils.send(exchange, "Login success");
            } else {
                Utils.send(exchange, "Login failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Utils.send(exchange, "Error");
        }
    }
}
