package com.coherentsolutions.util;

import com.coherentsolutions.building.Building;
import com.coherentsolutions.building.Floor;
import com.coherentsolutions.person.Person;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class PersonCreator extends Thread implements ElevatorInterface {
    private final Building building;
    private final int speed;
    private final int minWeight;
    private final int maxWeight;

    @Getter
    public boolean isRunning;
    private final Random random;

    private PersonCreator(Building building, int minWeight, int maxWeight, int speed) {
        checkArgument(speed >= MIN_SPEED && speed <= MAX_SPEED);
        checkArgument(minWeight >= 0);
        checkArgument(maxWeight <= 200);
        checkArgument(maxWeight >= minWeight);
        checkNotNull(building);
        this.speed = speed;
        this.minWeight = minWeight;
        this.building = building;
        this.maxWeight = maxWeight;
        this.random = new Random();
    }

    public static PersonCreator of(Building building, int minWeight, int maxWeight, int speed) {
        return new PersonCreator(building, minWeight, maxWeight, speed);
    }


    public void createHuman() {
        Floor floor = building.getFloor(Math.abs(random.nextInt()) % building.getNumberOfFloors());
        int weight = Math.abs(random.nextInt()) % (maxWeight - minWeight) + minWeight;
        int targetFloor;
        do {targetFloor = Math.abs(random.nextInt()) % building.getNumberOfFloors();
        } while (targetFloor == floor.getFloorNumber());
        floor.addPerson(Person.of(weight, targetFloor, floor));
        Statistics.getInstance().incrementCreatedPeople();
        try {TimeUnit.MILLISECONDS.sleep(DEFAULT_OPERATION_TIME - speed);
        } catch (InterruptedException exception) {
            log.error("human creator stopped");
            log.error(exception.getMessage());
            Thread.currentThread().interrupt();
        } log.info("human has been created at {}", targetFloor);
    }

    public void turnOff() {
        isRunning = false;
    }
    public void turnOn() {
        isRunning = true;
    }

    @Override
    public void run() {
        turnOn();
        while (isRunning && !isInterrupted()) {
            createHuman();
        }
    }
}
