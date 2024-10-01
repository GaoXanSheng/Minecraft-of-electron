package top.yunmouren.electron.Browser.tools;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SimpleTcpClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public SimpleTcpClient(String host, int port) {
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            new Thread(this::listenForMessages).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        this.out.println(message);
    }

    private String receiveMessage() {
        try {
            return in.readLine(); // 确保服务器发送的消息包含换行符
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void listenForMessages() {
        try {
            while (receiveMessage() != null) {
                System.out.println(receiveMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
