package firebase;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirebaseClient {

    private static final String FIREBASE_DB_URL =
            "https://chain-reaction-68ffb-default-rtdb.asia-southeast1.firebasedatabase.app";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void pushMove(String gameId, OnlineMove move) {
        try {

            String endpoint =
                    FIREBASE_DB_URL + "/games/" + gameId + "/moves.json";

            System.out.println(endpoint);

            URL url = new URL(endpoint);

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty(
                    "Content-Type",
                    "application/json"
            );

            connection.setDoOutput(true);

            String json = objectMapper.writeValueAsString(move);

            try(OutputStream os = connection.getOutputStream()) {
                os.write(json.getBytes());
            }

            int responseCode = connection.getResponseCode();

            System.out.println(
                    "Move pushed to Firebase. Response: "
                            + responseCode
            );

            connection.disconnect();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}