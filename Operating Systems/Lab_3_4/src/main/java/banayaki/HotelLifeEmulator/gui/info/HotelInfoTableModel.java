package banayaki.HotelLifeEmulator.gui.info;

import banayaki.HotelLifeEmulator.logic.entityes.HumanEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.table.DefaultTableModel;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("Duplicates")
@Component("hotelModel")
public class HotelInfoTableModel extends DefaultTableModel implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(HotelInfoTableModel.class);
    private BlockingQueue hotelInfoQueue;
    private Thread thread;

    private static final String[] COLUMN_NAMES = {"Name", "Value"};
    private final int COLUMN_COUNT = 2;
    private static final char MINUS = '-';
    private static final char PLUS = '+';

    public HotelInfoTableModel() {
        super(COLUMN_NAMES, 0);
        addRow(new Object[]{"Count solo rooms: ", 8});
        addRow(new Object[]{"Count double rooms: ", 6});
        addRow(new Object[]{"Free places count: ", 20});
        addRow(new Object[]{"In queue: ", 0});
        addRow(new Object[]{"Mans IN count: ", 0});
        addRow(new Object[]{"Womans IN count: ", 0});
        thread = new Thread(this, "HotelTableModel");
        thread.setDaemon(true);
    }

    @Autowired
    @Resource(name = "hotelInfoQueue")
    public void setHotelInfoQueue(BlockingQueue hotelInfoQueue) {
        this.hotelInfoQueue = hotelInfoQueue;
        thread.start();
    }

    @Override
    public void run() {
        logger.info("Now listens to the queue");
        try {
            while (true) {
                String msg = hotelInfoQueue.take().toString();
                logger.info(this.thread.getName() + " Get the message: " + msg);
                tableUpdate(msg);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tableUpdate(String val) {
        String[] splitter = val.split(" ");
        String prefix = splitter[0];

        if (prefix.startsWith("+")) {
            changeTableValue(MINUS, 2);
            if (splitter[1].equals(String.valueOf(HumanEntity.MAN_GENDER))) {
                changeTableValue(PLUS, 4);
            } else {
                changeTableValue(PLUS, 5);
            }
            changeTableValue(MINUS, 3);
        } else if (prefix.startsWith("-")) {
            changeTableValue(PLUS, 2);
            if (splitter[1].equals(String.valueOf(HumanEntity.MAN_GENDER))) {
                changeTableValue(MINUS, 4);
            } else {
                changeTableValue(MINUS, 5);
            }
        } else if (prefix.startsWith("q")) {
            changeTableValue(PLUS, 3);
        } else {
            changeTableValue(MINUS, 3);
        }
    }

    private void changeTableValue(char direction, int row) {
        if (direction == '+') {
            setValueAt((int) getValueAt(row, 1) + 1, row, 1);
        } else {
            setValueAt((int) getValueAt(row, 1) - 1, row, 1);
        }
    }
}
