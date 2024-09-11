package top.yunmouren.electron.Browser.tools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import top.yunmouren.electron.Client.Client;
import top.yunmouren.electron.Client.Network.ClientEnumeration;
import top.yunmouren.electron.Electron;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Http {
    public Http() {
        new Thread(() -> {
            try {
                HttpServer server = HttpServer.create(new InetSocketAddress(Client.browser.ServerPort), 0);
                server.createContext("/", new CmdHandler());
                server.setExecutor(null); // 使用默认的执行器
                server.start();
                Electron.logger.info("Server started on " + Client.browser.ServerPort);
            } catch (IOException e) {
                Electron.logger.error(e.getMessage());
            }
        }).start();
    }

    static class CmdHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // 读取请求体
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                JsonObject json = new Gson().fromJson(isr, JsonObject.class);
                // 获取 type 字段的值
                String type = json.get("type").getAsString();
                String body = json.get("body").getAsString();
                new ClientEnumeration(type, body);

                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, 0);
                JsonObject response = new JsonObject();
                // 构造响应体
                response.addProperty("status", true);
                OutputStream os = exchange.getResponseBody();
                os.write(response.toString().getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    public static String post(String webUrl, String json) {
        String charset = "UTF-8";
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn;
        try {
            URL url = new URL(webUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            //发送POST请求必须设置为true
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json;charset=" + charset);
            conn.setRequestProperty("Accept", "application/json");
            //获取输出流
            out = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
            out.write(json);
            out.flush();
            out.close();
            //取得输入流，并使用Reader读取
            if (200 == conn.getResponseCode()) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            } else {
                System.out.println("在运行中发生错误:" + conn.getResponseCode());
                System.out.println(webUrl);
            }
        } catch (Exception e) {
            System.out.println("在运行中发生错误:" + e.getMessage());
            System.out.println(webUrl);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                System.out.println("在运行中发生错误:" + ioe.getMessage());
                System.out.println(webUrl);
            }
        }
        return result.toString();
    }
}
