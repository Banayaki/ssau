package banayaki.HotelLifeEmulator.gui.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.concurrent.BlockingQueue;

@Component("eventModel")
public class EventListModel extends DefaultListModel<String> implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(EventListModel.class);

    private BlockingQueue eventInfoQueue;
    private Thread thread;

    public EventListModel() {
        addElement("List of events");
        this.setSize(1);
        thread = new Thread(this, "EventListModel");
        thread.setDaemon(true);
    }

    @Autowired
    @Resource(name = "eventInfoQueue")
    public void setEventInfoQueue(BlockingQueue eventInfoQueue) {
        logger.info("Setting queue in EvenListModel");
        this.eventInfoQueue = eventInfoQueue;
        thread.start();
    }


    @Override
    public void run() {
        logger.info("Now listens to the queue");
        try {
            while (true) {
                String msg = eventInfoQueue.take().toString();
                logger.debug("Get the message: " + msg);
                insertNewString(msg);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void insertNewString(String msg) {
        try {
            if (size() == 99)
                removeElementAt(98);
            add(1, msg);
        } catch (Exception ignored) {}
    }
}
