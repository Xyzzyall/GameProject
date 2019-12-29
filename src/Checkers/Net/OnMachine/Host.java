package Checkers.Net.OnMachine;

import Checkers.GameThread;
import Checkers.Net.HostThread;

public class Host extends HostThread {
    public Host(int width, int height, int fps, String[] textures) {
        super(width, height, fps, textures);
    }

    public Host(GameThread thread){
        super(thread);
    }

    @Override
    public void acceptData(byte[] bytes) {
        pull(bytes);
    }

    @Override
    public byte[] sendData() {
        if (!sendingQueueIsEmpty()){
            return null;
        } else {
            return pop_bytes_to_send();
        }
    }
}
