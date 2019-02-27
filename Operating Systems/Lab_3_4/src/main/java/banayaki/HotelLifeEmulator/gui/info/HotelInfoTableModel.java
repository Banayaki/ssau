package banayaki.HotelLifeEmulator.gui.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.concurrent.BlockingQueue;

@Component("hotelModel")
public class HotelInfoTableModel extends DefaultTableModel {
    private BlockingQueue hotelInfoQueue;

    private static final String[] COLUMN_NAMES = {"Name", "Value"};
    private final int COLUMN_COUNT = 2;


    public HotelInfoTableModel() {
        super(COLUMN_NAMES, 0);
    }

    @Autowired
    @Resource(name = "hotelInfoQueue")
    public void setHotelInfoQueue(BlockingQueue hotelInfoQueue) {
        this.hotelInfoQueue = hotelInfoQueue;
    }
}
