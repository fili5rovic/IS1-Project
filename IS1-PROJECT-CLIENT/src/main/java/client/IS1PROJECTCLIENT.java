package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IS1PROJECTCLIENT {
    
    private static final String SERVER_URL = "http://localhost:8080/IS1-PROJECT-SERVER/";
    
    public static void main(String[] args) {
        try {
            sendGet("api/test/allPlaces");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendGet(String url) throws IOException {
        URL obj = new URL(SERVER_URL + url);
        
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        
        String response = readConnection(con);
        
        System.out.println("Response: " + response);
    }
    
    private static String readConnection(HttpURLConnection con) throws IOException {
        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        
        in.close();
        
        return response.toString();
    }
}