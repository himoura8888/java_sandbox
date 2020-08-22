package io.himoura;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RaceCondition {

    static List<String> guests = new ArrayList<>();
    Lock lockGuests = new ReentrantLock();

    static {
        guests.add("naruto");
    }

    /**
     * @param guestName
     * @return true if guest is added else false
     */
    boolean addGuest(final String guestName){

        if(isAlreadyInvited(guestName)) {
            return false;
        }
        else{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            guests.add(guestName);
        }
        return true;
    }
    /**
     * @param guestName
     * @return true if guest is added else false
     */
     boolean addGuestThreadSafe(final String guestName){
        lockGuests.lock();
        if(isAlreadyInvited(guestName)) {
            lockGuests.unlock();
            return false;
        }
        else{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            guests.add(guestName);
            lockGuests.unlock();
        }
        return true;
    }

    private boolean isAlreadyInvited(String guestName) {
        return guests.contains(guestName);
    }

}
