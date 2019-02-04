package banayaki.HotelLifeEmulator.gui.main;

import banayaki.HotelLifeEmulator.gui.map.MapView;

import javax.swing.*;

public class MainPanel {

    private JPanel leftGroupPanel;
    private JPanel rightGroupPanel;
    private JPanel centerGroupPanel;
    private JPanel hotelInfoPanel;
    private JPanel hotelEventsPanel;
    private JPanel mapPanel;
    private JPanel controlButtonsPanel;
    private JPanel threadInfoPanel;
    private JList eventList;
    private JButton pauseButton;
    private JButton stopButton;
    private JButton startButton;
    private JTable hotelInfoTable;
    private JTable threadInfoTable;
    private JScrollPane hotelInfoScrollPane;
    private JScrollPane eventListScrollPane;
    private JScrollPane threadInfoScrollPane;
    public JPanel rootPanel;

    public MainPanel() {

    }


    public static void main(String[] args) {
    }

    private void createUIComponents() {
        mapPanel = new MapView();
    }
}
