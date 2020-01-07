package Checkers;
import Checkers.Menu.*;
import Checkers.Menu.Action;
import Checkers.Net.ClientThread;
import Checkers.Net.HostThread;
import Checkers.Net.Threads.Client;
import Checkers.Net.Threads.Host;

import javax.swing.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MenuThread extends Thread {
    private Frame currentFrame;
    private MenuActions actionsHandler;
    private JFrame frame;
    private GameThread gameThread;
    private MenuFeedback menuFeedback;

    public MenuThread(GameThread gameThread){
        actionsHandler = new MenuActions(this);
        frame = new JFrame("Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameThread = gameThread;
        this.menuFeedback = new MenuFeedback(this);
    }

    @Override
    public void run() {
        super.run();
        currentFrame = new MainFrame(actionsHandler, null);
        currentFrame.setJFrame(frame);
        currentFrame.show();

    }

    static class MenuActions extends Action {
        private MenuThread parent;

        public MenuActions(MenuThread menuThread){
            parent = menuThread;
        }

        private MultiplayerFrame.MultiplayerFrameFeedback multFeedback = null;

        @Override
        public void accept(Action.Actions actions) {
            System.out.println(actions);
            switch (actions){
                case EXIT:
                    //parent.close();
                    System.exit(0);
                    break;
                case START_MULTIPLAYER_GAME:
                    MultiplayerFrame multiplayerFrame = new MultiplayerFrame(this, multFeedback, parent.menuFeedback);
                    parent.changePane(multiplayerFrame);
                    multFeedback = new MultiplayerFrame.MultiplayerFrameFeedback(multiplayerFrame);
                    parent.currentFrame.show();
                    try(final DatagramSocket socket = new DatagramSocket()){
                        socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                        String ip = socket.getLocalAddress().getHostAddress();
                        multFeedback.data = ip;
                        multFeedback.accept(Feedback.Feedbacks.IP_ADDRESS);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    //parent.startDebugBridge();
                    break;
                case SINGLE_GAME:
                    parent.startSingleGame();
                    break;
                case BACK_TO_MAIN_MENU:
                    parent.changePane(new MainFrame(this, null));
                    parent.currentFrame.show();
                    //parent.stopDebugBridge();
                    break;
                case OPEN_SETTINGS:
                    parent.startDebugBridge();
                    break;
                case CONNECT_TO_SERVER:
                    if (parent.clientThread != null){
                        parent.clientThread.udp.stopped = true;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    parent.clientThread = new Client(parent.gameThread);
                    try {
                        parent.clientThread.setUdp(InetAddress.getByName("localhost"), 1000, parent.otherIP, 1001);
                        parent.clientThread.udp.init(false);
                    } catch (SocketException e) {
                        e.printStackTrace();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    parent.clientThread.start();
                    break;
                case CREATE_HOST:
                    if (parent.hostThread != null){
                        parent.hostThread.udp.stopped = true;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    parent.hostThread = new Host(parent.gameThread);
                    try {
                        parent.hostThread.setUdp(InetAddress.getByName("localhost"), 1001, parent.otherIP, 1000);
                        parent.hostThread.udp.init(false);
                    } catch (SocketException e) {
                        e.printStackTrace();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    parent.hostThread.start();
                    break;
            }
        }
    }

    private InetAddress otherIP;

    public static class MenuFeedback extends Feedback {
        private MenuThread parent;
        public MenuFeedback(MenuThread parent){
            this.parent = parent;
        }

        @Override
        public void accept(Feedbacks feedbacks) {
            switch (feedbacks){
                case CONNECT_TO_HOST:
                    try {
                        parent.otherIP = InetAddress.getByName(data);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    private void close(){
        currentFrame.close();
    }

    private void changePane(Frame frame){
        close();
        currentFrame = frame;
        currentFrame.setJFrame(this.frame);
    }

    private void startSingleGame(){
        frame.setVisible(false);
        gameThread.run();
        frame.setVisible(true);
    }

    private void stopDebugBridge(){
        if (bridgeThread != null)
            bridgeThread.stop = true;
    }

    private ClientThread clientThread;
    private HostThread hostThread;

    private BridgeThread bridgeThread;
    private void startDebugBridge(){
        this.bridgeThread = new BridgeThread(new Client(gameThread), new Host(gameThread));
        bridgeThread.start();
    }
}
