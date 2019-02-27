package banayaki.HotelLifeEmulator.gui.helpers;

import banayaki.HotelLifeEmulator.gui.info.EventListModel;
import banayaki.HotelLifeEmulator.gui.info.HotelInfoTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("unchecked")
@Service("threadController")
public class ThreadsController {
    private ThreadsSpawner spawner;
    private BlockingQueue eventInfoQueue;
    private BlockingQueue hotelInfoQueue;
    private BlockingQueue threadsInfoQueue;

    public void setEventInfoQueue(BlockingQueue eventInfoQueue) {
        this.eventInfoQueue = eventInfoQueue;
    }

    public void setHotelInfoQueue(BlockingQueue hotelInfoQueue) {
        this.hotelInfoQueue = hotelInfoQueue;
    }

    public void setThreadsInfoQueue(BlockingQueue threadsInfoQueue) {
        this.threadsInfoQueue = threadsInfoQueue;
    }

    public ThreadsController() {
        spawner = new ThreadsSpawner();
    }

    public void startEndlessSpawn() {
        eventInfoQueue.add("asdsafasfdf");
        spawner.getThread().start();
    }

    public void putMessage(Object message, Class clazz) {
        if (clazz.getName().equals(EventListModel.class.getName())) {
            eventInfoQueue.add(message);
        } else if (clazz.getName().equals(HotelInfoTableModel.class.getName())) {
            hotelInfoQueue.add(message);
        } else {
            threadsInfoQueue.add(message);
        }
    }
}
