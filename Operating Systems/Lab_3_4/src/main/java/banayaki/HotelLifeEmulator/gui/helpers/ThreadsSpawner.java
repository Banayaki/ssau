package banayaki.HotelLifeEmulator.gui.helpers;

import banayaki.HotelLifeEmulator.logic.entityes.HumanEntity;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class ThreadsSpawner extends ThreadGroup implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ThreadsSpawner.class);
    private Thread thread;

    public ThreadsSpawner() {
        super("Guests thread group");
        thread = new Thread((Runnable) this, "Guest Thread Spawner");
    }

    @Override
    public void run() {
        logger.info("Start spawn");
        try {
            Random random = new Random();
            for (int i = 0; i < 100; i++) {
                HumanEntity humanEntity;
                double num = random.nextGaussian();
                if (num > 0)
                    humanEntity = new HumanEntity(this, 'M');
                else
                    humanEntity = new HumanEntity(this, 'W');
                logger.info("Spawn the entity: " + humanEntity.toString());
                humanEntity.start();
                Thread.sleep(random.nextInt(2000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Thread getThread() {
        return thread;
    }
}
