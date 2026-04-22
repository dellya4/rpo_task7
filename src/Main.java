import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import database.Database;
import handler.CommentHandler;
import handler.CommentsHandler;
import handler.LoginHandler;
import handler.RegisterHandler;
import services.CommentService;
import services.SessionManager;
import services.UserService;

public class Main {
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        Database db = new Database();

        UserService userService = new UserService(db);
        CommentService commentService = new CommentService(db);
        SessionManager sessionManager = new SessionManager();

        server.createContext("/register", new RegisterHandler(userService));
        server.createContext("/login", new LoginHandler(userService, sessionManager));
        server.createContext("/comment", new CommentHandler(commentService, sessionManager));
        server.createContext("/comments", new CommentsHandler(commentService));

        server.start();

        System.out.println("Server started on 8080");
    }
}