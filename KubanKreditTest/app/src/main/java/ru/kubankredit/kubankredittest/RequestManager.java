package ru.kubankredit.kubankredittest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class RequestManager {
    private static int CONNECTION_TIMEOUT = 1000;
    private static int HTTP_WARNING = 461;

    public static String getResponseFromURL(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        connection.setReadTimeout(CONNECTION_TIMEOUT);

        try {

            final StringBuilder content = new StringBuilder();
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                content.append("Success\n");
            } else if (HttpURLConnection.HTTP_INTERNAL_ERROR == connection.getResponseCode()) {
                content.append("Error\n");
            } else if (HttpURLConnection.HTTP_UNAUTHORIZED == connection.getResponseCode() || HTTP_WARNING == connection.getResponseCode()) {
                content.append("Warning\n");
            }

            return content.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        } finally {
            connection.disconnect();
        }

    }
}
