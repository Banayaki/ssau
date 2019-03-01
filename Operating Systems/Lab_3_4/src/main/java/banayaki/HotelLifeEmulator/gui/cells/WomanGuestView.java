package banayaki.HotelLifeEmulator.gui.cells;

import banayaki.HotelLifeEmulator.gui.helpers.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class WomanGuestView extends View implements ActiveView{
    private static final String PATH = "src/main/resources/WomanGuest.png";
    private int width = 100;
    private int height = 100;
    private int x;
    private int y;

    public WomanGuestView(Pair<Integer, Integer> pair) {
        x = pair.fst;
        y = pair.snd;
    }

    @Override
    public void update() {

    }

    @SuppressWarnings("Duplicates")
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Image image;
        try {
            image = ImageIO.read(new File(PATH));
            g2.drawImage(image, x, y, width, height, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
