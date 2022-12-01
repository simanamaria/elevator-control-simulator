package com.coherentsolutions.util;

import com.coherentsolutions.building.Building;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.TimeUnit;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class BuildingInterface extends Thread implements ElevatorInterface {
    private final Building building;
    private final int acceptedSpeed;
    @Getter
    private boolean isRunning;

    private BuildingInterface(Building building, int acceptedSpeed) {
        checkNotNull(building);
        checkArgument(acceptedSpeed <= MAX_SPEED && acceptedSpeed >= MIN_SPEED);
        this.building = building;
        this.acceptedSpeed = acceptedSpeed;
        this.setName("userInterface");
    }
    public static BuildingInterface of(Building building, int acceptedSpeed) {
        return new BuildingInterface(building, acceptedSpeed);
    }

    public void printBuilding() {
        System.out.printf("Delivered: ", Statistics.getInstance().getDeliveredPeople());
        System.out.printf("Generated: ", Statistics.getInstance().getCreatedPeople());
        System.out.printf("Floors passed: ", Statistics.getInstance().getPassedFloors());
    }
    @Override
    public void turnOff() {
        isRunning = false;
    }

    @Override
    public void turnOn() {
        isRunning = true;
    }

    @Override
    public void run() {
        turnOn();
        while (isRunning && !isInterrupted()) {waitForOperation();}
    }

    private void waitForOperation() {
        try {
            TimeUnit.MILLISECONDS.sleep(DEFAULT_OPERATION_TIME - acceptedSpeed);
        } catch (InterruptedException exception) {
            log.error("user interface stopped");
            log.error(exception.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}