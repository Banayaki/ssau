package banayaki.HotelLifeEmulator.gui.map;

import banayaki.HotelLifeEmulator.gui.cells.ManGuestView;
import banayaki.HotelLifeEmulator.gui.cells.View;
import banayaki.HotelLifeEmulator.gui.cells.WomanGuestView;
import banayaki.HotelLifeEmulator.gui.helpers.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Component
public class MapView extends JPanel implements Runnable, ActionListener {
    private static final Logger logger = LoggerFactory.getLogger(MapView.class);
    private BlockingQueue messageProvider;
    private Thread thread = new Thread(this, "MapViewThread");

    private List<View> queueView = new LinkedList<>();
    private List<View> soloRoomsView = new LinkedList<>();
    private List<View> doubleRoomsView = new LinkedList<>();
    private View hotelView;

    private Timer timer = new Timer(1000, this);

    private static List<Pair<Integer, Integer>> inQueueCoord = new ArrayList<>();

    static {
        inQueueCoord.add(new Pair<>(1005, 848));
        inQueueCoord.add(new Pair<>(900, 848));
        inQueueCoord.add(new Pair<>(795, 848));
        inQueueCoord.add(new Pair<>(690, 848));
        inQueueCoord.add(new Pair<>(585, 848));
        inQueueCoord.add(new Pair<>(480, 848));
        inQueueCoord.add(new Pair<>(375, 848));
        inQueueCoord.add(new Pair<>(270, 848));
        inQueueCoord.add(new Pair<>(165, 848));
    }

    private MapViewFactory factory;
    private int orderNumber = 0;

    public MapView() {
        factory = new MapViewFactory();
        logger.info("First paint of hotelView");
        getHotelView();
    }

    @Resource(name = "mapViewQueue")
    public void setMessageProvider(BlockingQueue messageProvider) {
        logger.info("Setting up mapViewQueue");
        this.messageProvider = messageProvider;
        thread.start();
    }

    public void drawManGuestInQueue() {
        queueView.add(new ManGuestView(inQueueCoord.get(orderNumber)));
    }

    public void drawWomanGuestInQueue() {
        queueView.add(new WomanGuestView(inQueueCoord.get(orderNumber)));
    }

    public void drawOutTheQueue() {
        queueView.remove(orderNumber);
    }

    public void drawWomanInRoom(int roomNumber) {

    }

    public void drawManInRoom(int roomNumber) {

    }

    private void getHotelView() {
        hotelView = factory.hotelDrawer();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        logger.info("Paint mapView!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        hotelView.draw(g);
        for (View s : queueView) {
            s.draw(g);
        }
        for (View s : soloRoomsView) {
            s.draw(g);
        }
        for (View s : doubleRoomsView) {
            s.draw(g);
        }
    }

    @Override
    public void run() {
        timer.start();
        while (true) {
            try {
                String msg = (String) messageProvider.take();
                String[] splitter = msg.split(" ");
                if (splitter[0].equals("q")) {
                    if (splitter[1].equals("M")) {
                        drawManGuestInQueue();
                    }
                    else if (splitter[1].equals("W"))
                        drawWomanGuestInQueue();
                } else if (splitter[0].equals("l"))
                    drawOutTheQueue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.info("Call revalidate + repaint + paintImmediately has no effect =(");
//        EventQueue.invokeLater(() -> {
            revalidate();
            repaint();
//        });

    }
}
