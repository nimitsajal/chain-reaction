package firebase;

public class FirebaseTest {

    public static void main(String[] args) {

        FirebaseSSEListener listener =
                new FirebaseSSEListener();

        listener.listenForMoves("test-game");
    }
}