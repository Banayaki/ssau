package banayaki.HotelLifeEmulator.logic.entityes;

import banayaki.HotelLifeEmulator.gui.helpers.ThreadSpawner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;
import java.util.StringJoiner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope(value = "prototype")
public class HumanEntity extends Thread {

    private static final AtomicInteger ID = new AtomicInteger(0);
    private static final Logger logger = LoggerFactory.getLogger(HumanEntity.class);
    private final HotelEntity hotel = HotelEntity.getInstance();

    public static final char MAN_GENDER = 'M';
    public static final char WOMAN_GENDER = 'W';

    private BlockingQueue eventInfoQueue;
    private BlockingQueue hotelInfoQueue;

    private char gender;
    private RoomEntity room;
    private int timeToLive = 5000 + new Random().nextInt(5000);
    private String humanId;
    private ThreadSpawner messageProvider;

    public HumanEntity(ThreadGroup group, @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") char gender) {
        super(group, HumanEntity.class.getName() + ID.addAndGet(1));
        this.gender = gender;
    }

    public void setHumanId(String humanId) {
        this.humanId = humanId;
    }

    public void setMessageProvider(ThreadSpawner messageProvider) {
        this.messageProvider = messageProvider;
    }

    public char getGender() {
        return gender;
    }

    public void live() {
        try {
            boolean isInHotel = hotel.toServe(this);

            if (isInHotel) {
                messageProvider.packMessage(
                        "Now live in hotel: " + humanId,
                        "+ " + this.getGender()
                );

                Thread.sleep(timeToLive);
                hotel.toEvict(this, room);

                messageProvider.packMessage(
                        "Move out from the hotel: " + humanId,
                        "- " + this.getGender()
                );
            } else {
                messageProvider.packMessage(
                        "No places for: " + humanId,
                        "l "
                );
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HumanEntity.class.getSimpleName() + "[", "]")
                .add("name=" + this.getName())
                .add("gender=" + gender)
                .add("liveTime=" + timeToLive + "ms")
                .toString();
    }

    @Override
    public void run() {
        live();
    }

    @Autowired
    @Resource(name = "eventInfoQueue")
    public void setEventInfoQueue(BlockingQueue eventInfoQueue) {
        logger.info("Setting eventInfoQueue");
        this.eventInfoQueue = eventInfoQueue;
    }

    @Autowired
    @Resource(name = "hotelInfoQueue")
    public void setHotelInfoQueue(BlockingQueue hotelInfoQueue) {
        logger.info("Setting hotelInfoQueue");
        this.hotelInfoQueue = hotelInfoQueue;
    }
}
