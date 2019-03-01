package banayaki.HotelLifeEmulator.gui.helpers;

import banayaki.HotelLifeEmulator.gui.info.EventListModel;
import banayaki.HotelLifeEmulator.gui.info.HotelInfoTableModel;
import banayaki.HotelLifeEmulator.gui.map.MapView;
import banayaki.HotelLifeEmulator.logic.entityes.HumanEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

@Service("threadSpawner")
public class ThreadSpawner extends ThreadGroup implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ThreadSpawner.class);
    private Thread thread;

    private BlockingQueue eventInfoQueue;
    private BlockingQueue hotelInfoQueue;
    private BlockingQueue mapViewQueue;

    public void setEventInfoQueue(BlockingQueue eventInfoQueue) {
        this.eventInfoQueue = eventInfoQueue;
    }

    public void setHotelInfoQueue(BlockingQueue hotelInfoQueue) {
        this.hotelInfoQueue = hotelInfoQueue;
    }

    public void setMapViewQueue(BlockingQueue mapViewQueue) {
        this.mapViewQueue = mapViewQueue;
    }

    public ThreadSpawner() {
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
                if (num > 0) {
                    humanEntity = new HumanEntity(this, 'M');
                } else {
                    humanEntity = new HumanEntity(this, 'W');
                }

                String humanId = humanEntity.getClass().getSimpleName() + "#" + humanEntity.getId();
                humanEntity.setHumanId(humanId);
                humanEntity.setMessageProvider(this);

                packMessage(
                        "Stood in queue: " + humanId,
                        "q " + humanEntity.getGender()
                );
                logger.debug("Spawn the entity: " + humanEntity.toString());

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

    public void packMessage(Object eventMsg, Object hotelMsg) {
        putMessage(eventMsg, EventListModel.class);
        putMessage(hotelMsg, HotelInfoTableModel.class);
        putMessage(hotelMsg, MapView.class);
    }

    public void putMessage(Object message, Class clazz) {
        if (clazz.getName().equals(EventListModel.class.getName())) {
            eventInfoQueue.add(message);
        } else if (clazz.getName().equals(HotelInfoTableModel.class.getName())) {
            hotelInfoQueue.add(message);
        } else if (clazz.getName().equals(MapView.class.getName())) {
            mapViewQueue.add(message);
        }
    }
}
