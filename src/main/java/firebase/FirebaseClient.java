package firebase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class FirebaseClient {

    private static final String FIREBASE_DB_URL =
            "https://chain-reaction-68ffb-default-rtdb.asia-southeast1.firebasedatabase.app";

    private static final ObjectMapper objectMapper =
            new ObjectMapper();

    private static String buildUrl(String path) {

        return FIREBASE_DB_URL + path + ".json";
    }

    private static HttpURLConnection createConnection(
            String method,
            String endpoint
    ) throws Exception {

        URL url = new URL(endpoint);

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(method);

        connection.setRequestProperty(
                "Content-Type",
                "application/json"
        );

        connection.setDoOutput(true);

        return connection;
    }

    public static void pushMove(
            String gameId,
            OnlineMove move
    ) {

        try {

            String endpoint =
                    buildUrl(
                            "/games/" + gameId + "/moves"
                    );

            HttpURLConnection connection =
                    createConnection(
                            "POST",
                            endpoint
                    );

            String json =
                    objectMapper.writeValueAsString(move);

            try (OutputStream os =
                         connection.getOutputStream()) {

                os.write(json.getBytes());
            }

            System.out.println(
                    "Move pushed. Response: "
                            + connection.getResponseCode()
            );

            connection.disconnect();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void createNewGame(
            String gameId,
            GameMetadata gameMetadata
    ) {

        try {

            String endpoint =
                    buildUrl("/games/" + gameId);

            HttpURLConnection connection =
                    createConnection(
                            "PUT",
                            endpoint
                    );

            String json =
                    objectMapper.writeValueAsString(
                            gameMetadata
                    );

            try (OutputStream os =
                         connection.getOutputStream()) {

                os.write(json.getBytes());
            }

            System.out.println(
                    "Game created. Response: "
                            + connection.getResponseCode()
            );

            connection.disconnect();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static Map<String, Game> getGames() {

        try {

            String endpoint =
                    buildUrl("/games");

            HttpURLConnection connection =
                    createConnection(
                            "GET",
                            endpoint
                    );

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    connection.getInputStream()
                            )
                    );

            StringBuilder response =
                    new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {

                response.append(line);
            }

            connection.disconnect();

            return objectMapper.readValue(
                    response.toString(),
                    new TypeReference<>() {
                    }
            );

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}