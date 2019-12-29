package Checkers;
import Checkers.Menu.Action;
import Checkers.Menu.Frame;
import Checkers.Menu.MainFrame;

import javax.accessibility.Accessible;
import javax.swing.*;

public class MenuThread extends Thread {
    private Frame currentFrame;
    private MenuActions actionsHandler;
    private JFrame frame;

    public MenuThread(){
        actionsHandler = new MenuActions(this);
        frame = new JFrame("Checkers");
    }

    @Override
    public void run() {
        super.run();
        currentFrame = new MainFrame(actionsHandler);
        currentFrame.setJFrame(frame);
        currentFrame.show();

    }

    static class MenuActions extends Action {
        private MenuThread parent;

        public MenuActions(MenuThread menuThread){
            parent = menuThread;
        }

        @Override
        public void accept(Action.Actions actions) {
            System.out.println(actions);
            switch (actions){
                case EXIT:
                    //parent.close();
                    System.exit(0);
                    break;
            }
        }
    }

    private void close(){
        currentFrame.close();
    }
}
