package Checkers.Menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends Frame {
    private JButton SingleGameButton;
    private JButton MultiplayerGameButton;
    private JButton SettingsButton;
    private JButton ExitButton;
    private JPanel Main;

    public MainFrame(Action actionsHandler, Feedback feedback) {
        super(actionsHandler, feedback);
        SingleGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handle_action(Action.Actions.SINGLE_GAME);
            }
        });
        MultiplayerGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handle_action(Action.Actions.START_MULTIPLAYER_GAME);
            }
        });
        SettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handle_action(Action.Actions.OPEN_SETTINGS);
            }
        });
        ExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handle_action(Action.Actions.EXIT);
            }
        });
        mainPanel = Main;
    }
}
