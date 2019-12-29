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

    public boolean stop = false;

    @Override
    public void run() {
        super.run();
        client.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        host.start();

        while (!stop){
            byte[] from_client = client.sendData();
            byte[] from_host = host.sendData();
            if (from_client != null){
                host.acceptData(from_client);
            }
            if (from_host != null){
                client.acceptData(from_host);
            }
        }
    }
}
