package banayaki.HotelLifeEmulator.gui.main;

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
        setResizable(false);
//        setIconImage();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setSize(new Dimension(300, 400));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void setupComponents() {
        add(new MainPanel());
    }

    public static void main(String[] args) {
        new MainWindow();
    }

}
