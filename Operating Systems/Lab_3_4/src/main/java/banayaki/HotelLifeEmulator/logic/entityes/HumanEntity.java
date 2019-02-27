package banayaki.HotelLifeEmulator.logic.entityes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope(value = "prototype")
public class HumanEntity extends Thread {

    private static final AtomicInteger ID = new AtomicInteger();
    private final HotelEntity hotel = HotelEntity.getInstance();

    public static final char MAN_GENDER = 'M';
    public static final char WOMAN_GENDER = 'W';

    private char gender;
    private RoomEntity room;
    private int timeToLive = 5000 + new Random().nextInt(5000);

    public HumanEntity(ThreadGroup group, char gender) {
        super(group, HumanEntity.class.getName() + ID.addAndGet(1));
        this.gender = gender;
    }

    public char getGender() {
        return gender;
    }

    public void live() {
        try {
            boolean isInHotel = hotel.toServe(this);
            if (isInHotel) {
                Thread.sleep(timeToLive);
                hotel.toEvict(this, room);
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
}
