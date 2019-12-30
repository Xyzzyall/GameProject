package Checkers.Net;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class UDP extends Thread{
    private final int MAX_TRIES = 10;
    private DatagramSocket socket;
    private DatagramPacket recieving;
    private DatagramPacket sending;

    InetAddress self;
    int port;
    InetAddress other;
    int otherPort;

    public UDP(InetAddress self, int port, InetAddress other, int otherPort){
        this.self = self;
        this.port = port;
        this.other = other;
        this.otherPort = otherPort;
    }

    public void init() throws SocketException {
        socket = new DatagramSocket(port, self);
        socket.connect(other, otherPort);
        int tries = 1;
        while (!socket.isConnected() && tries < MAX_TRIES){
            System.out.println("Trying to connect to " + other.toString() + ':' + otherPort + ". Tries=" + tries);
            tries += 1;
            socket.connect(other, otherPort);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!socket.isConnected()){
            throw new SocketException("Connection to " + other.toString() + ":" + otherPort + " is failed.");
        }

        System.out.println("Connection to " + other.toString() + ':' + otherPort + ". Tries=" + tries);
        recieving = new DatagramPacket(new byte[64], 64);
        sending = new DatagramPacket(new byte[64], 64, socket.getRemoteSocketAddress());
        this.start();
    }

    Stack<byte[]> received = new Stack<>();

    @Override
    public void run() {
        super.run();
        while (true){
            try {
                socket.receive(recieving);
            } catch (IOException e) {
                e.printStackTrace();
            }
            received.push(recieving.getData().clone());
        }


    }

    public byte[] receive() {
        byte[] res = received.pop();
        return res;
    }

    public void send(byte[] bytes) throws IOException {
        sending.setData(bytes);
        socket.send(sending);
    }
}
