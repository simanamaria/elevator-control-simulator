package com.coherentsolutions.building;

import com.coherentsolutions.building.enums.Direction;
import com.coherentsolutions.building.helper.Call;
import com.coherentsolutions.building.helper.Controller;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {

    @Test
    void testOf() {
        assertEquals(10, Building.of(10).getNumberOfFloors());
    }

    @Test
    void testOf2() {
        try {
            Building.of(0);
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    void testAddElevator() {
        Building ofResult = Building.of(10);
        assertSame(ofResult, ofResult.addElevator(Elevator.of(3, 10, 1, 1)));
    }

    @Test
    void testAddElevator2() {
        try {
            Building.of(10).addElevator(null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testSetController() {
        Building ofResult = Building.of(10);
        Controller empty = Controller.getEmpty();
        Building actualSetControllerResult = ofResult.setController(empty);
        assertSame(ofResult, actualSetControllerResult);
        assertSame(empty, actualSetControllerResult.getController());
    }

    @Test
    void testSetController2() {
        try {
            Building.of(10).setController(null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testStart() {
        try {
            Building.of(10).start();
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testStart2() {
        Building ofResult = Building.of(10);
        try {
            ofResult.setController(Controller.getEmpty());
            ofResult.start();
            fail();
        } catch (IllegalStateException ex) {
            assertTrue(ex instanceof IllegalStateException);
        }
    }

    @Test
    void testStart3() {
        Building ofResult = Building.of(10);
        ofResult.addElevator(Elevator.of(3, 10, 1, 1));
        ofResult.setController(Controller.getEmpty());
        assertSame(ofResult, ofResult.start());
    }

    @Test
    void testStart4() {
        Building ofResult = Building.of(10);
        ofResult.addElevator(Elevator.of(3, 10, 1, 1));
        ofResult.addElevator(Elevator.of(3, 10, 1, 1));
        ofResult.setController(Controller.getEmpty());
        assertSame(ofResult, ofResult.start());
    }

    @Test
    void testStart5() {
        Building ofResult = Building.of(10);
        ofResult.addElevator(Elevator.of(3, 10, 1, 1000));
        ofResult.addElevator(Elevator.of(3, 10, 1, 1));
        ofResult.addElevator(Elevator.of(3, 10, 1, 1));
        ofResult.setController(Controller.getEmpty());
        assertSame(ofResult, ofResult.start());
    }

    @Test
    void testStart6() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1000);
        ofResult.addCall(Call.of(10, Direction.UP));
        ofResult.addTo(Building.of(10));
        Building ofResult1 = Building.of(10);
        ofResult1.addElevator(ofResult);
        ofResult1.addElevator(Elevator.of(3, 10, 1, 1));
        ofResult1.addElevator(Elevator.of(3, 10, 1, 1));
        ofResult1.setController(Controller.getEmpty());
        assertSame(ofResult1, ofResult1.start());
    }

    @Test
    void testStartElevators() {
        try {
            Building.of(10).startElevators();
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testStartElevators2() {
        Building ofResult = Building.of(10);
        try {
            ofResult.setController(Controller.getEmpty());
            ofResult.startElevators();
            fail();
        } catch (IllegalStateException ex) {
            assertTrue(ex instanceof IllegalStateException);
        }
    }

    @Test
    void testStartElevators3() {
        Building ofResult = Building.of(10);
        ofResult.addElevator(Elevator.of(3, 10, 1, 1));
        ofResult.setController(Controller.getEmpty());
        assertSame(ofResult, ofResult.startElevators());
    }

    @Test
    void testStartElevators4() {
        Building ofResult = Building.of(10);
        ofResult.addElevator(Elevator.of(3, 10, 1, 1));
        ofResult.addElevator(Elevator.of(3, 10, 1, 1));
        ofResult.setController(Controller.getEmpty());
        assertSame(ofResult, ofResult.startElevators());
    }

    @Test
    void testStartElevators5() {
        Building ofResult = Building.of(10);
        ofResult.addElevator(Elevator.of(3, 10, 1, 1));
        ofResult.addElevator(Elevator.of(3, 10, 1, 1));
        ofResult.addElevator(Elevator.of(3, 10, 1, 1));
        ofResult.addElevator(Elevator.of(3, 10, 1, 1));
        ofResult.setController(Controller.getEmpty());
        assertSame(ofResult, ofResult.startElevators());
    }

    @Test
    void testStartController() {
        try {
            Building.of(10).startController();
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testStartController2() {
        Building ofResult = Building.of(10);
        try {
            ofResult.setController(Controller.getEmpty());
            ofResult.startController();
            fail();
        } catch (IllegalStateException ex) {
            assertTrue(ex instanceof IllegalStateException);
        }

    }

    @Test
    void testStartController3() {
        Building ofResult = Building.of(10);
        ofResult.addElevator(Elevator.of(3, 10, 1, 1));
        ofResult.setController(Controller.getEmpty());
        assertSame(ofResult, ofResult.startController());
    }

    @Test
    void testGetFloor() {
        try {
            Building.of(10).getFloor(10);
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }


    @Test
    void testGetFloor3() {
        try {
            Building.of(10).getFloor(-1);
            fail();
        } catch (ArrayIndexOutOfBoundsException ex) {
            assertTrue(ex instanceof ArrayIndexOutOfBoundsException);
        }
    }
}

