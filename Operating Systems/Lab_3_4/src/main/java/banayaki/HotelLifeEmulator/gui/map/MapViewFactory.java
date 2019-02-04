package banayaki.HotelLifeEmulator.gui.map;

import banayaki.HotelLifeEmulator.gui.cells.HotelView;
import banayaki.HotelLifeEmulator.gui.cells.View;

import java.util.ArrayList;
import java.util.List;

class MapViewFactory {

    MapViewFactory() { }


    List<View> hotelDrawer() {
        List<View> list = new ArrayList<>();
        list.add(new HotelView());
        return list;
    }
}
