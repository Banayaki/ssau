package banayaki.HotelLifeEmulator.gui.info;

import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HotelInfoTableModel extends DefaultTableModel {

    private static final String[] COLUMN_NAMES = {"Name", "Value"};
    private final int COLUMN_COUNT = 2;
    private final Component PARENT;


    public HotelInfoTableModel(Component parent) {
        super(COLUMN_NAMES, 0);
        PARENT = parent;
    }
}
