package banayaki.HotelLifeEmulator.logic.entityes;

import java.util.concurrent.atomic.AtomicInteger;

public class    HumanEntity extends Thread {

    private static final AtomicInteger ID = new AtomicInteger();
    private final HotelEntity hotel = HotelEntity.getInstance();

    public static final char MAN_GENDER = 'M';
    public static final char WOMAN_GENDER = 'W';

    private char gender;
    private RoomEntity room;

    public HumanEntity(ThreadGroup group, char gender) {
        super(group, HumanEntity.class.getName() + ID.addAndGet(1));
        this.gender = gender;
    }

    public char getGender() {
        return gender;
    }

    public void live() {
        System.out.println(this.getName() + " alive");
        try {
            boolean isInHotel = hotel.toServe(this);
            if (isInHotel) {
                System.out.println(this.getName() + " live in hotel");
                Thread.sleep(1000);
                room.evict(this);
                System.out.println(this.getName() + " not in hotel now");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }


    @Override
    public void run() {
        live();
    }
}
