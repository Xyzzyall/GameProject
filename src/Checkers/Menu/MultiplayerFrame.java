package Checkers.Menu;

import Checkers.MenuThread;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiplayerFrame extends Frame{
    private JButton BackButton;
    private JPanel Multiplayer;
    private JButton ExitButton;
    private JButton ConnectButton;
    private JTextField otherIP;
    private JButton HostButton;
    private JCheckBox ConnectedCheckBox;
    private JCheckBox PlayerConnectedCheckBox;
    private JButton StartGameButton;
    private JCheckBox OtherPlayerCheckBox;
    private JLabel labelIP;

    private MenuThread.MenuFeedback menuFeedback;

    public MultiplayerFrame(Action actionsHandler, MultiplayerFrameFeedback feedback, MenuThread.MenuFeedback menuFeedback) {
        super(actionsHandler, feedback);
        this.menuFeedback = menuFeedback;
        ExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionsHandler.accept(Action.Actions.EXIT);
            }
        });
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionsHandler.accept(Action.Actions.BACK_TO_MAIN_MENU);
            }
        });
        mainPanel = Multiplayer;

        ConnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFeedback.data = otherIP.getText();
                menuFeedback.accept(Feedback.Feedbacks.CONNECT_TO_HOST);
                actionsHandler.accept(Action.Actions.CONNECT_TO_SERVER);
            }
        });
        HostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFeedback.data = otherIP.getText();
                menuFeedback.accept(Feedback.Feedbacks.CONNECT_TO_HOST);
                actionsHandler.accept(Action.Actions.CREATE_HOST);
            }
        });
    }

    public static class MultiplayerFrameFeedback extends Feedback{
        MultiplayerFrame frame;

        public MultiplayerFrameFeedback(MultiplayerFrame frame){
            this.frame = frame;
        }

        @Override
        public void accept(Feedbacks feedbacks) {
            switch (feedbacks){
                case IP_ADDRESS:
                    frame.labelIP.setText(frame.labelIP.getText().replace("@", data));
            }

        }
    }
}
