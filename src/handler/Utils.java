package handler;

import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static Map<String, String> parseForm(HttpExchange exchange) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));

        String formData = br.readLine();
        Map<String, String> map = new HashMap<>();

        if (formData != null) {
            String[] pairs = formData.split("&");

            for (String pair : pairs) {
                String[] kv = pair.split("=");
                String key = kv[0];
                String value = URLDecoder.decode(kv[1], "UTF-8");
                map.put(key, value);
            }
        }
        return map;
    }

    public static void send(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Security-Policy", "default-src 'self'");
        exchange.getResponseHeaders().add("X-Frame-Options", "DENY");
        exchange.getResponseHeaders().add("X-Content-Type-Options", "nosniff");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public static String getSessionId(HttpExchange exchange) {
        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        if (cookie == null) return null;

        for (String c : cookie.split(";")) {
            if (c.trim().startsWith("sessionId=")) {
                return c.split("=")[1];
            }
        }
        return null;
    }

    public static String escapeHtml(String input) {
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}