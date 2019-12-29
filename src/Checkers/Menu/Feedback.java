package Checkers.Menu;

import java.util.function.Consumer;

public abstract class Feedback implements Consumer<Feedback.Feedbacks> {
    public String data;
    public enum Feedbacks{
        IP_ADDRESS,
        PLAYER_CONNECTED,
        YOU_CONNECTED,
        OPPONENT_IS_READY
    }
}
