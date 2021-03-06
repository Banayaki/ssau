package banayaki.HotelLifeEmulator.gui.cells;

import banayaki.HotelLifeEmulator.gui.helpers.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ManGuestView extends View {
    private static final String PATH = "src/main/resources/ManGuest.png";
    private int width = 100;
    private int height = 100;
    private int x;
    private int y;

    public ManGuestView(Pair<Integer, Integer> pair) {
        x = pair.fst;
        y = pair.snd;
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
