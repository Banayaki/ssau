package banayaki.HotelLifeEmulator.gui.main;

import banayaki.HotelLifeEmulator.gui.helpers.ThreadsSpawner;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setupView();
        setupComponents();
        pack();
        setVisible(true);
    }

    private void setupView() {
        setTitle("Hotel Life Emulator");
        setResizable(true);
//        setIconImage();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(new Dimension(300, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void setupComponents() {
        setContentPane(new MainPanel().rootPanel);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(MainWindow::new);
        ThreadsSpawner spawner = new ThreadsSpawner();
        spawner.run();
    }

}
