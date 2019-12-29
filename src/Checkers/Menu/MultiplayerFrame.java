package Checkers.Menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiplayerFrame extends Frame{
    private JButton BackButton;
    private JPanel Multiplayer;
    private JButton ExitButton;
    private JButton ConnectButton;
    private JTextField textField1;
    private JButton HostButton;
    private JCheckBox ConnectedCheckBox;
    private JCheckBox PlayerConnectedCheckBox;
    private JButton StartGameButton;
    private JCheckBox OtherPlayerCheckBox;

    public MultiplayerFrame(Action actionsHandler, MultiplayerFrameFeedback feedback) {
        super(actionsHandler, feedback);
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
    }

    public static class MultiplayerFrameFeedback extends Feedback{
        @Override
        public void accept(Feedbacks feedbacks) {

        }
    }
}
