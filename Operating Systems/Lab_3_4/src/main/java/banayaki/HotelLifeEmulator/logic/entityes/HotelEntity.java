package banayaki.HotelLifeEmulator.logic.entityes;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class HotelEntity {

    private static Semaphore semaphore = new Semaphore(1, true);

    private static HotelEntity hotelEntity = new HotelEntity();
    private static Set<RoomEntity> doubleRooms;
    private static Set<RoomEntity> soloRooms;

    private static final int doubleRoomsCount = 6;
    private static final int soloRoomsCount = 8;

    private HotelEntity() {
        generateRooms();
    }

    public static HotelEntity getInstance() {
        return hotelEntity;
    }

    public boolean toServe(HumanEntity human) {
        boolean isInHotel = false;
        try {
            semaphore.acquire();
            RoomEntity room = checkFreeRoom(human.getGender());
            if (room != null) {
                room.occupy(human);
                isInHotel = true;
            }
            Thread.sleep(1000);
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isInHotel;
    }

    public void toEvict(HumanEntity humanEntity, RoomEntity roomEntity) {
        try {
            semaphore.acquire();
            roomEntity.evict(humanEntity);
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private RoomEntity checkFreeRoom(char gender) {
        String target;
        if (gender == HumanEntity.MAN_GENDER)
            target = RoomEntity.ENGAGE_BY_WOMAN;
        else
            target = RoomEntity.ENGAGE_BY_MAN;

        RoomEntity result = null;
        RoomEntity room;

        for (RoomEntity doubleRoom : doubleRooms) {
            room = doubleRoom;
            if (room.engageBy().equals(target))
                return room;
            else if (room.engageBy().equals(RoomEntity.NOT_ENGAGED))
                result = room;
        }
        if (result != null)
            return result;
        else {
            for (RoomEntity soloRoom : soloRooms) {
                if (soloRoom.engageBy().equals(RoomEntity.NOT_ENGAGED)) {
                    return soloRoom;
                }
            }
        }

        return result;
    }

    private void generateRooms() {
        soloRooms = new HashSet<>();
        for (int i = 0; i < soloRoomsCount; ++i) {
            soloRooms.add(new RoomEntity(1));
        }

        doubleRooms = new HashSet<>();
        for (int i = 0; i < doubleRoomsCount; ++i) {
            doubleRooms.add(new RoomEntity(2));
        }
    }

}
