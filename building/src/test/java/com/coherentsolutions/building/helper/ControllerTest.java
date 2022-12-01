package com.coherentsolutions.building.helper;

import com.coherentsolutions.building.Elevator;
import com.coherentsolutions.building.enums.Direction;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void testGetEmpty() {
        assertFalse(Controller.getEmpty().isRunning());
    }

    @Test
    void testCanCallElevator() {
        Controller empty = Controller.getEmpty();
        assertTrue(empty.canCallElevator(Call.of(10, Direction.UP)));
    }

    @Test
    void testCanCallElevator2() {
        try {
            Controller.getEmpty().canCallElevator(null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testCanCallElevator3() {
        ArrayList<Elevator> elevatorList = new ArrayList<>();
        elevatorList.add(Elevator.of(3, 10, 1, 1));
        Controller empty = Controller.getEmpty();
        empty.setElevators(elevatorList);
        assertTrue(empty.canCallElevator(Call.of(10, Direction.UP)));
    }

    @Test
    void testCanCallElevator4() {
        ArrayList<Elevator> elevatorList = new ArrayList<>();
        elevatorList.add(Elevator.of(3, 10, 1, 1));
        elevatorList.add(Elevator.of(3, 10, 1, 1));
        Controller empty = Controller.getEmpty();
        empty.setElevators(elevatorList);
        assertTrue(empty.canCallElevator(Call.of(10, Direction.UP)));
    }

    @Test
    void testCanCallElevator5() {
        ArrayList<Elevator> elevatorList = new ArrayList<>();
        elevatorList.add(Elevator.of(3, 1, 1, 1));
        Controller empty = Controller.getEmpty();
        empty.setElevators(elevatorList);
        assertTrue(empty.canCallElevator(Call.of(10, Direction.UP)));
    }

    @Test
    void testCanCallElevator6() {
        Controller empty = Controller.getEmpty();
        ArrayList<Elevator> elevatorList = new ArrayList<>();
        try {
            elevatorList.add(null);
            empty.setElevators(elevatorList);
            empty.canCallElevator(Call.of(10, Direction.UP));
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testCanCallElevator7() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.addCall(Call.of(10, Direction.UP));

        ArrayList<Elevator> elevatorList = new ArrayList<>();
        elevatorList.add(ofResult);
        Controller empty = Controller.getEmpty();
        empty.setElevators(elevatorList);
        assertTrue(empty.canCallElevator(Call.of(10, Direction.UP)));
    }

    @Test
    void testCanCallElevator8() {
        ArrayList<Elevator> elevatorList = new ArrayList<>();
        elevatorList.add(Elevator.of(3, 10, 1, 1));
        Controller empty = Controller.getEmpty();
        empty.setElevators(elevatorList);
        assertTrue(empty.canCallElevator(Call.of(10, Direction.NONE)));
    }

    @Test
    void testAddCall() {
        Controller empty = Controller.getEmpty();
        empty.addCall(Call.of(10, Direction.UP));
    }

    @Test
    void testAddCall2() {
        try {
            Controller.getEmpty().addCall(null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testRemoveCall() {
        Controller empty = Controller.getEmpty();
        empty.removeCall(Call.of(10, Direction.UP));
    }

    @Test
    void testRemoveCall2() {
        try {
            Controller.getEmpty().removeCall(null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testTurnOff() {
        Controller empty = Controller.getEmpty();
        empty.turnOff();
        assertFalse(empty.isRunning());
    }

    @Test
    void testTurnOn() {
        Controller empty = Controller.getEmpty();
        empty.turnOn();
        assertTrue(empty.isRunning());
    }

    @Test
    void testRun() {
        Controller empty = Controller.getEmpty();
        empty.run();
        assertTrue(empty.isRunning());
    }

}

