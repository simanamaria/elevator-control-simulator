package com.coherentsolutions.building.helper;

import com.coherentsolutions.building.Elevator;
import com.coherentsolutions.building.enums.Direction;
import com.coherentsolutions.building.enums.State;
import com.coherentsolutions.util.ElevatorInterface;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Controller implements Runnable, ElevatorInterface {
    @Setter
    private List<Elevator> elevators;
    private final Queue<Call> calls;

    @Getter
    private volatile boolean isRunning;

    public List<Elevator> getElevators() {
        return elevators;
    }

    public Queue<Call> getCalls() {
        return calls;
    }

    private Controller() {
        this.elevators = new ArrayList<>();
        this.calls = new LinkedList<>();
        this.isRunning = false;
    }

    public static Controller getEmpty() {
        return new Controller();
    }
    public boolean canCallElevator(Call call) {
        if (call == null){throw new NullPointerException();
        } else return elevators.stream()
                .noneMatch(i -> (i.getDirection().equals(call.getDirection()) || i.getDirection().equals(Direction.NONE))
                        && i.getCurrentFloorNumber() == call.getTargetFloorNumber()
                        && (i.getState().equals(State.ENTER) || i.getState().equals(State.OPEN_DOOR)));
    }

    public void addCall(Call call) {
        if (call == null) throw new NullPointerException();
        if(call.getTargetFloorNumber() < 0) throw new IllegalArgumentException();
        calls.add(call);
        log.info("call added: {}", call.getTargetFloorNumber());
    }

    public void removeCall(Call call) {
        if (call == null) throw new NullPointerException();
        calls.removeAll(calls.stream().filter(call::equals).collect(Collectors.toList()));
        log.info("call has been removed {}", call);
    }

    public void turnOff() {
        isRunning = false;
        log.info("controller stopped");
    }

    public void turnOn() {
        isRunning = true;
        log.info("controller started");
    }

    @Override
    public void run() {
        turnOn();
        }
    }

