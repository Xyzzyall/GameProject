package Checkers.Menu;

import javax.swing.*;

public class Frame {
    protected Action actionsHandler;
    protected Feedback feedbackHandler;
    protected JPanel mainPanel;
    protected JFrame frame;

    public Frame(Action actionsHandler, Feedback feedbackHandler) {
        this.actionsHandler = actionsHandler;
        this.feedbackHandler = feedbackHandler;
    }

    protected void handle_action(Action.Actions action){
        this.actionsHandler.accept(action);
    }

    public void setJFrame(JFrame frame){
        this.frame = frame;
    }

    public void show(){
        frame.setContentPane(this.mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public void close(){
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
