package Checkers;
import Checkers.Menu.Action;
import Checkers.Menu.Frame;
import Checkers.Menu.MainFrame;
import Checkers.Menu.MultiplayerFrame;
import Checkers.Net.OnMachine.Client;
import Checkers.Net.OnMachine.Host;

import javax.swing.*;

public class MenuThread extends Thread {
    private Frame currentFrame;
    private MenuActions actionsHandler;
    private JFrame frame;
    private GameThread gameThread;

    public MenuThread(GameThread gameThread){
        actionsHandler = new MenuActions(this);
        frame = new JFrame("Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameThread = gameThread;
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
            multFeedback = new MultiplayerFrame.MultiplayerFrameFeedback();
        }

        private MultiplayerFrame.MultiplayerFrameFeedback multFeedback;

        @Override
        public void accept(Action.Actions actions) {
            System.out.println(actions);
            switch (actions){
                case EXIT:
                    //parent.close();
                    System.exit(0);
                    break;
                case START_MULTIPLAYER_GAME:
                    parent.changePane(new MultiplayerFrame(this, multFeedback));
                    parent.currentFrame.show();
                    parent.startDebugBridge();
                    break;
                case SINGLE_GAME:
                    parent.startSingleGame();
                    break;
                case BACK_TO_MAIN_MENU:
                    parent.changePane(new MainFrame(this, null));
                    parent.currentFrame.show();
                    parent.stopDebugBridge();
                    break;
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

    private BridgeThread bridgeThread;
    private void startDebugBridge(){
        this.bridgeThread = new BridgeThread(new Client(gameThread), new Host(gameThread));
        bridgeThread.start();
    }
}
