package Checkers;

import Checkers.Net.ClientThread;
import Checkers.Net.HostThread;

public class BridgeThread extends Thread {
    private ClientThread client;
    private HostThread host;

    public BridgeThread(ClientThread client, HostThread host){
        this.client = client;
        this.host = host;
    }

    @Override
    public void run() {
        super.run();
    }
}
