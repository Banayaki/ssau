package banayaki.HotelLifeEmulator.gui.main;

import banayaki.HotelLifeEmulator.gui.info.EventListModel;
import banayaki.HotelLifeEmulator.gui.info.HotelInfoTableModel;
import banayaki.HotelLifeEmulator.gui.info.ThreadsTableModel;
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
    private JList<String> eventList;
    private JButton pauseButton;
    private JButton stopButton;
    private JButton startButton;
    private JTable hotelInfoTable;
    private JTable threadInfoTable;
    private JScrollPane hotelInfoScrollPane;
    private JScrollPane eventListScrollPane;
    private JScrollPane threadInfoScrollPane;
    public JPanel rootPanel;

    MainPanel() {
        setupTables();
        setupEventList();
    }

    private void createUIComponents() {
        mapPanel = new MapView();
    }


    private void setupTables() {
        hotelInfoTable.setModel(new HotelInfoTableModel(hotelInfoScrollPane));
        threadInfoTable.setModel(new ThreadsTableModel(threadInfoScrollPane));
    }

    private void setupEventList() {
        eventList.setDragEnabled(false);
        eventList.setAutoscrolls(true);
        eventList.setModel(new EventListModel());
    }
}
