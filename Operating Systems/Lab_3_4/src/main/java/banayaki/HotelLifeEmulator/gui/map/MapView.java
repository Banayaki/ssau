package banayaki.HotelLifeEmulator.gui.map;

import banayaki.HotelLifeEmulator.gui.cells.View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MapView extends JPanel {

    private List<View> shapes = new ArrayList<>();

    private MapViewFactory factory;

    public MapView() {
        factory = new MapViewFactory();
        drawMap();
    }

    public void drawManGuestInQueue() {

    }

    public void drawWomanGuestInQueue() {

    }

    public void drawWomanInRoom(int roomNumber) {

    }

    public void drawManInRoom(int roomNumber) {

    }

    private void drawMap() {
        shapes.addAll(factory.hotelDrawer());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (View s : shapes) {
            s.draw(g);
        }
    }
}
