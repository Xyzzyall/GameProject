package Checkers.Net;

import Checkers.Net.Wraps.Wrap;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.nio.ByteBuffer;
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

    public void init(boolean echo) throws SocketException {
        socket = new DatagramSocket(port, self);
        recieving = new DatagramPacket(new byte[64], 64);

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

        sending = new DatagramPacket(new byte[64], 64, socket.getRemoteSocketAddress());

        if (echo)
            try {
                sendEcho();
            } catch (IOException e) {
                e.printStackTrace();
            }


        this.start();
    }

    Stack<byte[]> received = new Stack<>();

    public boolean stopped = false;

    public void sendEcho() throws IOException {
        send(ByteBuffer.allocate(64).put(Wrap.ECHO_PACKET).array().clone());
    }

    public void sendEchoResponse() throws IOException {
        send(ByteBuffer.allocate(64).put(Wrap.ECHO_RESPONSE).array().clone());
    }

    public boolean echoed = false;

    @Override
    public void run() {
        super.run();
        while (!stopped){
            try {
                socket.receive(recieving);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (recieving.getData()[0] == Wrap.ECHO_PACKET || recieving.getData()[0] == Wrap.ECHO_RESPONSE){
                System.out.println("Got an echo packet from " + other.toString() + ".");
                if (recieving.getData()[0] == Wrap.ECHO_PACKET){
                    try {
                        sendEchoResponse();
                        echoed = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
            received.push(recieving.getData().clone());
            }
        }
        socket.close();
        socket = null;
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
