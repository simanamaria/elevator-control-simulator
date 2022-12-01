package com.coherentsolutions.util;


import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {
    private static volatile Statistics instance;
    private final AtomicInteger deliveredPeople;
    private final AtomicInteger createdPeople;
    private final AtomicInteger passedFloors;

    private Statistics() {
        deliveredPeople = new AtomicInteger(0);
        createdPeople = new AtomicInteger(0);
        passedFloors = new AtomicInteger(0);
    }

    public static Statistics getInstance() {
        Statistics localInstance = instance;
        if (localInstance == null) {
            synchronized (Statistics.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = new Statistics();
                    localInstance = instance;
                }
            }
        }

        return localInstance;
    }

    public void restart() {
        deliveredPeople.set(0);
        createdPeople.set(0);
        passedFloors.set(0);
    }
    public AtomicInteger getDeliveredPeople() {return deliveredPeople;}

    public AtomicInteger getCreatedPeople() {return createdPeople;}

    public AtomicInteger getPassedFloors() {return passedFloors;}
    public void incrementDeliveredPeople() {
        deliveredPeople.incrementAndGet();
    }
    public void incrementCreatedPeople() {
        createdPeople.incrementAndGet();
    }
    public void incrementPassedFloors() {
        passedFloors.incrementAndGet();
    }
}