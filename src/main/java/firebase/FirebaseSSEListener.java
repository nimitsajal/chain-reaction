package firebase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirebaseSSEListener {

    private static final String FIREBASE_DB_URL =
            "https://chain-reaction-68ffb-default-rtdb.asia-southeast1.firebasedatabase.app";

    public void listenForMoves(String gameId) {

        try {

            String endpoint =
                    FIREBASE_DB_URL
                            + "/games/"
                            + gameId
                            + "/moves.json";

            System.out.println("Listening to:");
            System.out.println(endpoint);

            URL url = new URL(endpoint);

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            connection.setRequestProperty(
                    "Accept",
                    "text/event-stream"
            );

            connection.setDoInput(true);

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    connection.getInputStream()
                            )
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                if (!line.isBlank()) {
                    System.out.println(line);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}