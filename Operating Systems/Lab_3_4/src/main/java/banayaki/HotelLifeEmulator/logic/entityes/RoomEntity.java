package banayaki.HotelLifeEmulator.logic.entityes;

public class RoomEntity {

    public static final String ENGAGE_BY_MAN = "man";
    public static final String ENGAGE_BY_WOMAN = "woman";
    public static final String NOT_ENGAGED = "free";
    public static final String FULL_ENGAGED = "full";


    private int countOfPlaces;
    private HumanEntity[] freePlaces;


    public RoomEntity(int countOfPlaces) {
        this.countOfPlaces = countOfPlaces;
        freePlaces = new HumanEntity[countOfPlaces];
    }

    public void occupy(HumanEntity human) {
        for (int i = 0; i < countOfPlaces; ++i) {
            if (freePlaces[i] == null) {
                freePlaces[i] = human;
                human.setRoom(this);
                return;
            }
        }
    }

    public void evict(HumanEntity human) {
        for (int i = 0; i < countOfPlaces; ++i) {
            if (freePlaces[i] != null && freePlaces[i].getName().equals(human.getName())) {
                freePlaces[i] = null;
            }
        }
    }

    public String engageBy() {
        int counter = 0;
        String returnVal = "";
        for (int i = 0; i < countOfPlaces; i++) {
            if (freePlaces[i] != null && freePlaces[i].getGender() == HumanEntity.MAN_GENDER) {
                ++counter;
                returnVal = ENGAGE_BY_MAN;
            }

            if (freePlaces[i] != null && freePlaces[i].getGender() == HumanEntity.MAN_GENDER) {
                ++counter;
                returnVal = ENGAGE_BY_WOMAN;
            }
        }
        if (counter == 2) return FULL_ENGAGED;
        if (counter == 0) return NOT_ENGAGED;
        return returnVal;
    }


}
