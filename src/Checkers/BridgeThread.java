package Checkers;

import Checkers.Net.ClientThread;
import Checkers.Net.HostThread;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class BridgeThread extends Thread {
    private ClientThread client;
    private HostThread host;

    public BridgeThread(ClientThread client, HostThread host){
        this.client = client;
        this.host = host;
        try {
            client.setUdp(InetAddress.getByName("localhost"), 1000, InetAddress.getByName("localhost"), 1001);
            host.setUdp(InetAddress.getByName("localhost"), 1001, InetAddress.getByName("localhost"), 1000);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
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

    }
}
