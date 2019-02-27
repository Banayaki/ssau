package banayaki.HotelLifeEmulator.gui.main;

import banayaki.HotelLifeEmulator.gui.info.HotelInfoTableModel;
import banayaki.HotelLifeEmulator.gui.info.ThreadsTableModel;
import banayaki.HotelLifeEmulator.gui.map.MapView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.*;

@Component("mainPanel")
public class MainPanel {

    private static final Logger logger = LoggerFactory.getLogger(MainPanel.class);

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
        logger.info("Creating mainPanel");
    }

    private void createUIComponents() {
        mapPanel = new MapView();
    }

    @Autowired
    @Resource(name = "eventModel")
    public void setEventModel(ListModel model) {
        logger.info("Set <eventModel> in mainPanel");
        eventList.setDragEnabled(false);
        eventList.setAutoscrolls(true);
        eventList.setModel(model);
    }

    @Autowired
    @Resource(name = "hotelModel")
    public void setHotelModel(HotelInfoTableModel model) {
        logger.info("Set <hotelModel> in mainPanel");
        hotelInfoTable.setModel(model);
    }

    @Autowired
    @Resource(name = "threadModel")
    public void setThreadModel(ThreadsTableModel model) {
        logger.info("Set <threadModel> in mainPanel");
        threadInfoTable.setModel(model);
    }

}
