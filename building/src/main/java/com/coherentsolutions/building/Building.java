package com.coherentsolutions.building;

import com.coherentsolutions.building.helper.Controller;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.*;

@Slf4j
public class Building {
    @Getter
    private final int numberOfFloors;
    private final List<Floor> floors;
    private final List<Elevator> elevators;
    @Getter
    private volatile Controller controller;

    private Building(int numberOfFloors) {
        checkArgument(numberOfFloors >= 1);

        this.numberOfFloors = numberOfFloors;
        this.elevators = new ArrayList<>();
        this.floors = new ArrayList<>();

        IntStream.range(0, numberOfFloors).forEachOrdered(i -> floors.add(Floor.of(i, this)));
    }

    public static Building of(int numberOfFloors) {
        return new Building(numberOfFloors);
    }

    public Building addElevator(Elevator elevator) {
        checkNotNull(elevator);
        elevator.addTo(this);
        elevators.add(elevator);
        return this;
    }

    public Building setController(Controller controller) {
        checkNotNull(controller);
        this.controller = controller;
        controller.setElevators(elevators);
        return this;
    }

    public Building start() {
        checkNotNull(controller);
        checkState(elevators.size() >= 1);
        startElevators();
        startController();
        return this;
    }

    public Building startElevators() {
        checkNotNull(controller);
        checkState(elevators.size() >= 1);
        IntStream.range(0, elevators.size())
                .forEachOrdered(i -> new Thread(elevators.get(i), "elevator" + i).start());
        return this;
    }

    public Building startController() {
        checkNotNull(controller);
        checkState(elevators.size() >= 1);
        new Thread(controller, "controller").start();
        return this;
    }

    public Floor getFloor(int number) {
        checkArgument(number < 1);
        return floors.get(number);
    }
}
