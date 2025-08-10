package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class RestUtil {

    private static final String SERVER_URL = "http://localhost:8080/IS1-PROJECT-SERVER/app/";

    public static void get(String url) {
        sendRequest(url, "GET", null);
    }

    public static void post(String url) {
        String resource = url.replaceAll(".*/([^/]+)/(JSON|new)$", "$1");
        String body = JsonUtil.readJson(resource);
        System.out.println(body);
        sendRequest(url, "POST", body);
    }
    
    public static void postNoBody(String url) {
        sendRequest(url, "POST", null);
    }

    public static void put(String url) {
        sendRequest(url, "PUT", null);
    }

    public static void delete(String url) {
        sendRequest(url, "DELETE", null);
    }

    private static void sendRequest(String url, String method, String body) {
        try {
            URL obj = new URL(SERVER_URL + url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Accept", "application/json");
            con.setConnectTimeout(10_000);
            con.setReadTimeout(10_000); 

            if ("POST".equals(method) && body != null && !body.isEmpty()) {
                con.setDoOutput(true);
                con.setRequestProperty("Content-Type", "application/json");
                byte[] input = body.getBytes("utf-8");
                con.setRequestProperty("Content-Length", String.valueOf(input.length));
                try (OutputStream os = con.getOutputStream()) {
                    os.write(input, 0, input.length);
                    os.flush();
                }
            }

            int responseCode = con.getResponseCode();
            String responseMessage = con.getResponseMessage();
            String rawResponse = readConnection(con);

            System.out.println("\n========== HTTP " + method + " REQUEST ==========");
            System.out.println("URL: " + obj.toString());
            if (body != null && !body.isEmpty()) {
                System.out.println("Request Body:");
                System.out.println(JsonUtil.format(body));
            }

            System.out.println("\n============= RESPONSE ==============");
            System.out.println("Status: " + responseCode + " " + responseMessage);
            System.out.println("Response Body:");
//            System.out.println(JsonUtil.format(rawResponse));
            JsonUtil.printJsonTable(JsonUtil.format(rawResponse));
            System.out.println("=====================================\n");
        } catch(SocketTimeoutException e) {
            System.out.println("Timed out");
        } catch (MalformedURLException e) {
            System.err.println("[ERROR] MalformedURLException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("[ERROR] IOException: " + e.getMessage());
        }
    }

    private static String readConnection(HttpURLConnection con) throws IOException {
        BufferedReader in;
        int responseCode = con.getResponseCode();
        if (responseCode >= 200 && responseCode < 400) {
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
        } else {
            in = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
        }

        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine).append('\n');
        }
        in.close();
        return response.toString().trim();
    }

}
