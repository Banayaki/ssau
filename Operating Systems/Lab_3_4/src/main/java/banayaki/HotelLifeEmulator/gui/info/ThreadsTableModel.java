package banayaki.HotelLifeEmulator.gui.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.concurrent.BlockingQueue;

@Component("threadModel")
public class ThreadsTableModel extends DefaultTableModel {
    private BlockingQueue threadInfoQueue;

    private static final String[] COLUMN_NAMES = {"Name", "State"};
    private final int COLUMN_COUNT = 2;


    public ThreadsTableModel() {
        super(COLUMN_NAMES, 0);
    }

    @Autowired
    @Resource(name = "threadInfoQueue")
    public void setThreadInfoQueue(BlockingQueue threadInfoQueue) {
        this.threadInfoQueue = threadInfoQueue;
    }
}
