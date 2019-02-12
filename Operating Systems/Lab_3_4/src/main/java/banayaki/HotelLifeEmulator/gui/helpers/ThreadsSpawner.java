package banayaki.HotelLifeEmulator.gui.helpers;

import banayaki.HotelLifeEmulator.logic.entityes.HumanEntity;

import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadsSpawner extends ThreadGroup {

    public ThreadsSpawner() {
        super("Guests thread group");
    }

    public void run() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            HumanEntity humanEntity;
            double num = random.nextGaussian();
            if (num > 0)
                humanEntity = new HumanEntity(this, 'M');
            else
                humanEntity = new HumanEntity(this, 'W');
            humanEntity.start();
        }
    }
}
