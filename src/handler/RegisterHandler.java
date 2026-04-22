package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import services.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class RegisterHandler implements HttpHandler {

    private UserService userService;

    public RegisterHandler(UserService userService) {
        this.userService = userService;
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
            userService.register(username, password);
            Utils.send(exchange, "User created");
        } catch (SQLException e) {
            e.printStackTrace();
            Utils.send(exchange, "Error");
        }
    }
}