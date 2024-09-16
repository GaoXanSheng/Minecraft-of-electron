package top.yunmouren.electron.Browser.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Http {
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
            e.printStackTrace();
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
