package banayaki.HotelLifeEmulator.gui.info;

import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ThreadsTableModel extends DefaultTableModel {

    private static final String[] COLUMN_NAMES = {"Name", "State"};
    private final int COLUMN_COUNT = 2;
    private final Component PARENT;


    public ThreadsTableModel(Component parent) {
        super(COLUMN_NAMES, 0);
        PARENT = parent;
    }
}
