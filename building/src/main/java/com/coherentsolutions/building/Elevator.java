package com.coherentsolutions.building;

import com.coherentsolutions.building.enums.Direction;
import com.coherentsolutions.building.enums.State;
import com.coherentsolutions.building.helper.Call;
import com.coherentsolutions.building.helper.Controller;
import com.coherentsolutions.person.Person;
import com.coherentsolutions.util.Statistics;
import com.coherentsolutions.util.ElevatorInterface;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.*;
@Slf4j
public class Elevator implements Runnable, ElevatorInterface {
    @Getter
    private final UUID number;
    @Getter
    private final int capacity;
    @Getter
    private final int elevatorSpeed;
    @Getter
    private final int doorSpeed;
    private final List<Person> people;
    private final List<Call> calls;
    @Getter
    private final AtomicInteger numberOfDeliveredPeople;
    private final AtomicInteger currentFloorNumber;
    private volatile boolean isRunning;
    private volatile Building building;
    private volatile Direction direction;
    private volatile State state;

    private Elevator(int capacity, int currentFloorNumber, int elevatorSpeed, int doorSpeed) {
        checkArgument(capacity > 0);
        checkArgument(elevatorSpeed >= MIN_SPEED && elevatorSpeed <= MAX_SPEED);
        checkArgument(doorSpeed >= MIN_SPEED && doorSpeed <= MAX_SPEED);
        this.number = UUID.randomUUID();
        this.capacity = capacity;
        this.elevatorSpeed = elevatorSpeed;
        this.doorSpeed = doorSpeed;
        this.currentFloorNumber = new AtomicInteger(currentFloorNumber);
        this.people = new ArrayList<>();
        this.calls = new ArrayList<>();
        this.direction = Direction.NONE;
        this.state = State.STOP;
        this.numberOfDeliveredPeople = new AtomicInteger(0);
    }
    
    public static Elevator of(int capacity, int startFloorNumber, int moveSpeed, int doorWorkSpeed) {
        return new Elevator(capacity, startFloorNumber, moveSpeed, doorWorkSpeed);
    }
    
    public void addTo(Building building) {
        checkNotNull(building);
        this.building = building;
    }

    public int getCurrentFloorNumber() {
        return currentFloorNumber.get();
    }

    public Floor getCurrentFloor() {
        return building.getFloor(currentFloorNumber.get());
    }

    public int getSpaceInElevator() {
        int engagedSpace = people.stream().mapToInt(Person::getWeight).sum();
        return capacity - engagedSpace;
    }
    public Controller getController() {
        checkNotNull(building);
        checkNotNull(building.getController());
        return building.getController();
    }

    public State getState() {
        return this.state;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public Direction getDestinationDirection() {
        return calls.isEmpty() ? Direction.NONE : calls.get(0).getDirection();
    }

    public List<Person> getPeople() {
        return ImmutableList.copyOf(people);
    }

    public List<Call> getCalls() {
        return ImmutableList.copyOf(calls);
    }

    public boolean isRunning() {
        return isRunning;
    }
    public void addCall(Call call) {
        checkNotNull(call);
        calls.add(call);
        if (direction == Direction.NONE) {
            direction = call.getTargetFloorNumber() - currentFloorNumber.get() > 0 ? Direction.UP : Direction.DOWN;
        }
        log.info("elevator called to {}", call);
    }

    public void goUp() {
        checkState(getCurrentFloorNumber() < building.getNumberOfFloors());
        direction = Direction.UP;
        state = State.MOVE;
        currentFloorNumber.incrementAndGet();
        Statistics.getInstance().incrementPassedFloors();
        try { TimeUnit.MILLISECONDS.sleep(DEFAULT_OPERATION_TIME - elevatorSpeed);
        } catch (InterruptedException exception){
            log.error("elevator was stopped");
            log.error(exception.getMessage());
            end();
            turnOff();
            Thread.currentThread().interrupt();
        }
        log.info("elevator went to floor number {}", currentFloorNumber);
    }

    public void goDown() {
        checkState(currentFloorNumber.get() > 0);
        direction = Direction.DOWN;
        state = State.MOVE;
        currentFloorNumber.decrementAndGet();
        Statistics.getInstance().incrementPassedFloors();
        try {
            TimeUnit.MILLISECONDS.sleep(DEFAULT_OPERATION_TIME - elevatorSpeed);
        } catch (InterruptedException exception){
            log.error("elevator was stopped");
            log.error(exception.getMessage());
            end();
            turnOff();
            Thread.currentThread().interrupt();
        }
        log.info("elevator went to floor number {}", currentFloorNumber);
    }

    public void openDoor() {
        state = State.OPEN_DOOR;
        try {
            TimeUnit.MILLISECONDS.sleep(DEFAULT_OPERATION_TIME - doorSpeed);
        } catch (InterruptedException exception){
            log.error("elevator cannot open door");
            log.error(exception.getMessage());
            end();
            turnOff();
            Thread.currentThread().interrupt();
        }
        log.info("elevator opened the doors");
    }

    public void enterInElevator(Person person) {
        checkNotNull(person);
        if (direction == Direction.NONE) {
            direction = person.getCall().getDirection();
        }
        people.add(person);
        getController().removeCall(Call.of(getCurrentFloorNumber(), person.getCall().getDirection()));
        addCall(person.getCall());
        try {TimeUnit.MILLISECONDS.sleep(DEFAULT_OPERATION_TIME - doorSpeed);
        } catch (InterruptedException exception){
            log.error("elevator was stopped");
            log.error(exception.getMessage());
            end();
            turnOff();
            Thread.currentThread().interrupt();
        } log.info("Person went in the elevator {}", person);
    }

    public void arrived(Person person) {
        checkNotNull(person);
        checkArgument(people.contains(person));
        people.remove(person);
        Statistics.getInstance().incrementDeliveredPeople();
        numberOfDeliveredPeople.incrementAndGet();
        try { TimeUnit.MILLISECONDS.sleep(DEFAULT_OPERATION_TIME - doorSpeed);
        } catch (InterruptedException exception){
            log.error("elevator cannot exit human");
            log.error(exception.getMessage());
            end();
            turnOff();
            Thread.currentThread().interrupt();
        } log.info("elevator exit the next human: {}", person);
    }

    public boolean checkFloor() {
        Person person = null;
        boolean result = false;
        Direction destinationDirection = getDestinationDirection();
        if (!destinationDirection.equals(Direction.NONE) && destinationDirection.equals(direction)) {
            person = getCurrentFloor().getFirstPerson(direction);}
        if (person != null && person.getWeight() <= getSpaceInElevator()) {
            result = person.getCall().getDirection() == direction;}
        return result;
    }

    public void load() {
        state = State.ENTER;
        handleExiting();
        handleLoad();
        handleEntering();
        log.info("elevator finishes load");}

    private void handleExiting() {
        List<Person> peopleExiting = people.stream()
                .filter(i -> i.getCall().getTargetFloorNumber() == currentFloorNumber.get())
                .collect(Collectors.toList());
        peopleExiting.forEach(this::arrived);
        log.info("elevator has finished exiting");
    }

    private void handleLoad() {
        if (people.isEmpty() && calls.isEmpty()) {
            log.info("elevator is empty");
            direction = Direction.NONE;
        } else if (people.isEmpty()) {
            direction = getDestinationDirection();}
    }
    private void handleEntering() {
        boolean isEntering = true;
        while (state == State.ENTER && isEntering) {
            Person person = getCurrentFloor().getFirstPerson(direction);
            Direction destinationDirection = getDestinationDirection();
            if (person != null && ((!destinationDirection.equals(Direction.NONE)
                    && destinationDirection.equals(person.getCall().getDirection()))
                    || (destinationDirection.equals(Direction.NONE)
                    && person.getCall().getDirection().equals(direction))
                    || direction.equals(Direction.NONE))) {
                if (person.getWeight() <= getSpaceInElevator()) {
                    if (direction.equals(Direction.NONE)) {
                        direction = person.getCall().getDirection();}
                    person = getCurrentFloor().pollFirstHuman(direction);
                    enterInElevator(person);
                    log.info("person was picked up {}", person);
                } else {
                    getController().addCall(Call.of(currentFloorNumber.get(), person.getCall().getDirection()));
                    log.info("elevator doesn't have enough space {}", person);
                    log.info("elevator recall {}", person.getCall());
                    isEntering = false;}
            } else { isEntering = false;}
        }
    }

    public void closeDoor() {
        state = State.CLOSE_DOOR;
        try {
            TimeUnit.MILLISECONDS.sleep(DEFAULT_OPERATION_TIME - doorSpeed);
        } catch (InterruptedException exception){
            log.error("couldn't close doors");
            log.error(exception.getMessage());
            end();
            turnOff();
            Thread.currentThread().interrupt();
        }
        log.info("elevator close doors");
    }

    public void stop() {
        direction = Direction.NONE;
        state = State.STOP;
        while (calls.isEmpty()) {
            log.info("elevator stopped");
                end();
                turnOff();
                Thread.currentThread().interrupt();
            }
        }

    public void end() {
        direction = Direction.NONE;
        state = State.STOP;
        log.warn("elevator finished");
    }

    public boolean removeFinishedFloors() {
        boolean hasExecutedCalls;
        List<Call> currentFloorCalls = calls.stream()
                .filter(i -> i.getTargetFloorNumber() == currentFloorNumber.get())
                .collect(Collectors.toList());
        hasExecutedCalls = calls.removeAll(currentFloorCalls);
        return hasExecutedCalls;
    }

    @Override
    public void turnOff() {
        isRunning = false;
        log.info("elevator has been stopped");
    }

    @Override
    public void turnOn() {
        isRunning = true;
        log.info("elevator has been started");
    }

    @Override
    public void run() {
        boolean arePeopleWaiting;
        boolean hasFinishedFloors;
        int currentFloorNumber;
        turnOn();
        while (isRunning) {
            if (calls.isEmpty()) stop();
            else {
                hasFinishedFloors = removeFinishedFloors();
                currentFloorNumber = calls.isEmpty()
                        ? this.currentFloorNumber.get()
                        : calls.get(0).getTargetFloorNumber();
                arePeopleWaiting = checkFloor();
                if (hasFinishedFloors || arePeopleWaiting) {
                    openDoor();
                    load();
                    closeDoor();
                } else if (currentFloorNumber > this.currentFloorNumber.get()) goUp();
                 else if (currentFloorNumber < this.currentFloorNumber.get()) goDown();
            }
        }
        turnOff();
        end();
    }

    @Override
    public String toString() {
        return String.format("State: %s; Direction: %s; Free space: %s; PeopleDelivered: %d; Calls: %s; Passengers: %s; "
                , getState(), getDirection(), getSpaceInElevator(), numberOfDeliveredPeople.get(), getCalls(), getPeople());
    }
}