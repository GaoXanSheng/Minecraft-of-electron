import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
public class HttpGetExample {
    public static String get(String webUrl) {
        String charset = "UTF-8";
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn;

        try {
            URL url = new URL(webUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // 设置连接超时时间和读取超时时间
            conn.setConnectTimeout(30000); // 30 秒连接超时
            conn.setReadTimeout(10000); // 10 秒读取超时
            conn.setRequestProperty("Content-Type", "application/json;charset=" + charset);
            conn.setRequestProperty("Accept", "application/json");

            // 检查响应状态码
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // 成功获取响应
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            } else {
                // 非 200 响应状态码
                System.out.println("Error during request. Response code: " + responseCode);
                System.out.println("Response message: " + conn.getResponseMessage());
                System.out.println("URL: " + webUrl);
            }
        } catch (Exception e) {
            // 捕获并打印异常
            System.out.println("Error during request: " + e.getMessage());
            e.printStackTrace(); // 输出详细的异常栈信息
            System.out.println("URL: " + webUrl);
        } finally {
            // 关闭 BufferedReader
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error closing reader: " + ioe.getMessage());
                ioe.printStackTrace();
            }
        }

        // 返回结果字符串
        return result.toString();
    }

    public static void main(String[] args) {
        // 调用 GET 请求并打印结果
        String response = get("http://localhost:59960/json");
        System.out.println("Response: " + response);
    }
}