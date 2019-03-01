package banayaki.HotelLifeEmulator.gui.helpers;

import banayaki.HotelLifeEmulator.gui.info.EventListModel;
import banayaki.HotelLifeEmulator.gui.info.HotelInfoTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

@SuppressWarnings("unchecked")
@Service("threadController")
public class ThreadsController {
    private ThreadSpawner spawner;

    @Autowired
    public void setSpawner(ThreadSpawner spawner) {
        this.spawner = spawner;
    }

    public void startEndlessSpawn() {
        spawner.getThread().start();
    }
}
