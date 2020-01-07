package Checkers.Net.Threads;

import Checkers.GameThread;
import Checkers.Net.HostThread;

public class Host extends HostThread {
    public Host(int width, int height, int fps, String[] textures) {
        super(width, height, fps, textures);
    }

    public Host(GameThread thread){
        super(thread);
    }
}
