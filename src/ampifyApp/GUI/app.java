package ampifyApp.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

public class app {
    private JButton play;
    private JButton pause;
    private JPanel playerScreen;
    private JButton stop;
    private JButton prev;
    private JButton next;

    static musicPlayer player = new musicPlayer();
    JDialog playingDialog;

    public app() {
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.pause();
            }
        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.play();
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.stop();
            }
        });

        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.prev();
            }
        });

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.next();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("app");
        frame.setContentPane(new app().playerScreen);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.showOpenDialog(null);
        File[] files = chooser.getSelectedFiles();
        player.start(files);
    }
}
