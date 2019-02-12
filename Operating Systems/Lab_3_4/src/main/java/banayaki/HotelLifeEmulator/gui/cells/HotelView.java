package banayaki.HotelLifeEmulator.gui.cells;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HotelView extends View {

    private static final String PATH = "src/main/resources/hotel.png";

    public HotelView() {

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Image image;
        try {
            image = ImageIO.read(new File(PATH));
            g2.drawImage(image, 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
