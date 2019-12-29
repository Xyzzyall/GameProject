package Checkers.Menu;

import javax.swing.*;

public class Frame {
    protected Action actionsHandler;
    protected JPanel mainPanel;
    protected JFrame frame;

    public Frame(Action actionsHandler) {
        this.actionsHandler = actionsHandler;
    }

    protected void handle_action(Action.Actions action){
        this.actionsHandler.accept(action);
    }

    public void setJFrame(JFrame frame){
        this.frame = frame;
    }

    public void show(){
        //frame.removeAll();
        frame.setContentPane(this.mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public void close(){
        //mainPanel.removeAll();
        //mainPanel = null;
    }
}
