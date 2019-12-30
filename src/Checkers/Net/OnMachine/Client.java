package Checkers.Net.OnMachine;

import Checkers.GameThread;
import Checkers.Net.ClientThread;

public class Client extends ClientThread {
    public Client(int width, int height, int fps, String[] textures) {
        super(width, height, fps, textures);
    }

    public Client(GameThread thread){
        super(thread);
    }
}
