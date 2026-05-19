package firebase;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MoveQueueManager {

    private static final BlockingQueue<OnlineMove>
            sharedMovesQueue =
            new LinkedBlockingQueue<>();

    public static void enqueueMove(
            OnlineMove move
    ) {

        try {

            sharedMovesQueue.put(move);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }

    public static OnlineMove waitForNextMove() {

        try {

            return sharedMovesQueue.take();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            return null;
        }
    }
}