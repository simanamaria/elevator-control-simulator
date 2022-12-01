package com.coherentsolutions.building;

import com.coherentsolutions.building.enums.Direction;
import com.coherentsolutions.building.enums.State;
import com.coherentsolutions.building.helper.Call;
import com.coherentsolutions.building.helper.Controller;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {

    @Test
    void testOf() {
        Elevator actualOfResult = Elevator.of(3, 10, 1, 1);
        String actualToStringResult = actualOfResult.toString();
        assertEquals(Direction.NONE, actualOfResult.getDirection());
        assertEquals(State.STOP, actualOfResult.getState());
        assertEquals("State: STOP; Direction: NONE; Free space: 3; PeopleDelivered: 0; Calls: []; Passengers: []; ",
                actualToStringResult);
    }

    @Test
    void testOf2() {
        Elevator actualOfResult = Elevator.of(3, 10, 1, 1);
        assertEquals(State.STOP, actualOfResult.getState());
        assertEquals(3, actualOfResult.getSpaceInElevator());
        assertEquals(3, actualOfResult.getCapacity());
        assertEquals(Direction.NONE, actualOfResult.getDestinationDirection());
        assertEquals(Direction.NONE, actualOfResult.getDirection());
        assertEquals(10, actualOfResult.getCurrentFloorNumber());
        assertEquals(1, actualOfResult.getDoorSpeed());
        assertEquals(1, actualOfResult.getElevatorSpeed());
    }

    @Test
    void testOf3() {
        try {
            Elevator.of(0, 1, 1, 1);
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    void testOf4() {
        try {
            Elevator.of(3, 10, 0, 1);
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    void testOf5() {
        try {
            Elevator.of(3, 10, 1, 0);
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }


    @Test
    void testAddTo2() {
        try {
            Elevator.of(3, 10, 1, 1).addTo(null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testGetCurrentFloorNumber() {
        assertEquals(10, Elevator.of(3, 10, 1, 1).getCurrentFloorNumber());
    }

    @Test
    void testGetCurrentFloor() {
        try {
            Elevator.of(3, 10, 1, 1).getCurrentFloor();
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testGetCurrentFloor2() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        try {
            ofResult.addTo(Building.of(10));
            ofResult.getCurrentFloor();
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }


    @Test
    void testGetCurrentFloor4() {
        Elevator ofResult = Elevator.of(3, -1, 1, 1);
        try {
            ofResult.addTo(Building.of(10));
            ofResult.getCurrentFloor();
            fail();
        } catch (ArrayIndexOutOfBoundsException ex) {
            assertTrue(ex instanceof ArrayIndexOutOfBoundsException);
        }
    }

    @Test
    void testGetSpaceInElevator() {
        assertEquals(3, Elevator.of(3, 10, 1, 1).getSpaceInElevator());
    }

    @Test
    void testGetController() {
        try {
            Elevator.of(3, 10, 1, 1).getController();
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testGetController2() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        try {
            ofResult.addTo(Building.of(10));
            ofResult.getController();
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testGetController3() {
        Building ofResult = Building.of(10);
        Controller empty = Controller.getEmpty();
        ofResult.setController(empty);
        Elevator ofResult1 = Elevator.of(3, 10, 1, 1);
        ofResult1.addTo(ofResult);
        assertSame(empty, ofResult1.getController());
    }

    @Test
    void testGetDestinationDirection() {
        assertEquals(Direction.NONE, Elevator.of(3, 10, 1, 1).getDestinationDirection());
    }

    @Test
    void testGetDestinationDirection2() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.addCall(Call.of(10, Direction.UP));
        assertEquals(Direction.UP, ofResult.getDestinationDirection());
    }

    @Test
    void testGetDestinationDirection3() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.addCall(Call.of(10, Direction.DOWN));
        assertEquals(Direction.DOWN, ofResult.getDestinationDirection());
    }

    @Test
    void testGetPeople() {
        assertTrue(Elevator.of(3, 10, 1, 1).getPeople().isEmpty());
    }

    @Test
    void testGetCalls() {
        assertTrue(Elevator.of(3, 10, 1, 1).getCalls().isEmpty());
    }

    @Test
    void testGetCalls2() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.addCall(Call.of(10, Direction.UP));
        assertEquals(1, ofResult.getCalls().size());
    }

    @Test
    void testAddCall() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.addCall(Call.of(10, Direction.UP));
        assertEquals(Direction.UP, ofResult.getDestinationDirection());
        assertEquals(Direction.DOWN, ofResult.getDirection());
    }

    @Test
    void testAddCall2() {
        try {
            Elevator.of(3, 10, 1, 1).addCall(null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testAddCall3() {
        Elevator ofResult = Elevator.of(3, 1, 1, 1);
        ofResult.addCall(Call.of(10, Direction.UP));
        assertEquals(Direction.UP, ofResult.getDestinationDirection());
        assertEquals(Direction.UP, ofResult.getDirection());
    }

    @Test
    void testAddCall4() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.addCall(Call.of(10, Direction.UP));
        ofResult.addCall(Call.of(10, Direction.UP));
        assertEquals(Direction.UP, ofResult.getDestinationDirection());
    }

    @Test
    void testGoUp() {
        try {
            Elevator.of(3, 10, 1, 1).goUp();
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testGoUp2() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        try {
            ofResult.addTo(Building.of(10));
            ofResult.goUp();
            fail();
        } catch (IllegalStateException ex) {
            assertTrue(ex instanceof IllegalStateException);
        }

    }

    @Test
    void testGoUp3() {
        Elevator ofResult = Elevator.of(3, 1, 1, 1);
        ofResult.addTo(Building.of(10));
        ofResult.goUp();
        assertEquals(State.MOVE, ofResult.getState());
        assertEquals(Direction.UP, ofResult.getDirection());
        assertEquals(2, ofResult.getCurrentFloorNumber());
    }

    @Test
    void testGoDown() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.goDown();
        assertEquals(State.MOVE, ofResult.getState());
        assertEquals(Direction.DOWN, ofResult.getDirection());
        assertEquals(9, ofResult.getCurrentFloorNumber());
    }

    @Test
    void testGoDown2() {
        Elevator ofResult = Elevator.of(10, 10, 1, 1);
        ofResult.goDown();
        assertEquals(State.MOVE, ofResult.getState());
        assertEquals(Direction.DOWN, ofResult.getDirection());
        assertEquals(9, ofResult.getCurrentFloorNumber());
    }

    @Test
    void testGoDown3() {
        try {
            Elevator.of(3, 0, 1, 1).goDown();
            fail();
        } catch (IllegalStateException ex) {
            assertTrue(ex instanceof IllegalStateException);
        }
    }

    @Test
    void testOpenDoor() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.openDoor();
        assertEquals(State.OPEN_DOOR, ofResult.getState());
    }

    @Test
    void testEnterInElevator() {
        try {
            Elevator.of(3, 10, 1, 1).enterInElevator(null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testArrived() {
        try {
            Elevator.of(3, 10, 1, 1).arrived(null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testCheckFloor() {
        assertFalse(Elevator.of(3, 10, 1, 1).checkFloor());
    }

    @Test
    void testCheckFloor2() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.addCall(Call.of(10, Direction.UP));
        assertFalse(ofResult.checkFloor());
    }

    @Test
    void testCheckFloor3() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        try {
            ofResult.addCall(Call.of(10, Direction.DOWN));
            ofResult.checkFloor();
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testCheckFloor4() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        try {
            ofResult.addTo(Building.of(10));
            ofResult.addCall(Call.of(10, Direction.DOWN));
            ofResult.checkFloor();
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    void testCheckFloor5() {
        Elevator ofResult = Elevator.of(3, Integer.MIN_VALUE, 1, 1);
        try {
            ofResult.addTo(Building.of(10));
            ofResult.addCall(Call.of(10, Direction.DOWN));
            ofResult.checkFloor();
            fail();
        } catch (ArrayIndexOutOfBoundsException ex) {
            assertTrue(ex instanceof ArrayIndexOutOfBoundsException);
        }
    }

    @Test
    void testLoad() {
        try {
            Elevator.of(3, 10, 1, 1).load();
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testLoad2() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        try {
            ofResult.addTo(Building.of(10));
            ofResult.load();
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    void testLoad3() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        try {
            ofResult.addCall(Call.of(10, Direction.UP));
            ofResult.addTo(Building.of(10));
            ofResult.load();
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    void testLoad4() {
        Elevator ofResult = Elevator.of(3, 0, 1, 1);
        ofResult.addTo(Building.of(10));
        ofResult.load();
        assertEquals(State.ENTER, ofResult.getState());
        assertEquals(Direction.NONE, ofResult.getDirection());
    }

    @Test
    void testLoad5() {
        Elevator ofResult = Elevator.of(3, -1, 1, 1);
        try {
            ofResult.addTo(Building.of(10));
            ofResult.load();
            fail();
        } catch (ArrayIndexOutOfBoundsException ex) {
            assertTrue(ex instanceof ArrayIndexOutOfBoundsException);
        }
    }

    @Test
    void testLoad6() {
        Elevator ofResult = Elevator.of(3, 0, 1, 1);
        ofResult.addCall(Call.of(10, Direction.UP));
        ofResult.addTo(Building.of(10));
        ofResult.load();
        assertEquals(State.ENTER, ofResult.getState());
        assertEquals(Direction.UP, ofResult.getDirection());
    }

    @Test
    void testCloseDoor() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.closeDoor();
        assertEquals(State.CLOSE_DOOR, ofResult.getState());
    }

    @Test
    void testStop() {

        Elevator elevator = null;
        try {
            elevator.stop();
            fail();
        } catch (NullPointerException e){
            assertTrue(e instanceof NullPointerException);
        }
    }

    @Test
    void testEnd() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.end();
        assertEquals(State.STOP, ofResult.getState());
        assertEquals(Direction.NONE, ofResult.getDirection());
    }

    @Test
    void testRemoveFinishedFloors() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        assertFalse(ofResult.removeFinishedFloors());
        assertEquals(Direction.NONE, ofResult.getDestinationDirection());
    }

    @Test
    void testRemoveFinishedFloors2() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.addCall(Call.of(10, Direction.UP));
        assertTrue(ofResult.removeFinishedFloors());
        assertEquals(Direction.NONE, ofResult.getDestinationDirection());
    }

    @Test
    void testRemoveFinishedFloors3() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.addCall(Call.of(10, Direction.UP));
        ofResult.addCall(Call.of(10, Direction.UP));
        assertTrue(ofResult.removeFinishedFloors());
        assertEquals(Direction.NONE, ofResult.getDestinationDirection());
    }

    @Test
    void testRemoveFinishedFloors4() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.addCall(Call.of(1, Direction.UP));
        assertFalse(ofResult.removeFinishedFloors());
        assertEquals(Direction.UP, ofResult.getDestinationDirection());
    }

    @Test
    void testRemoveFinishedFloors5() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.addCall(Call.of(1, Direction.UP));
        ofResult.addCall(Call.of(10, Direction.UP));
        assertTrue(ofResult.removeFinishedFloors());
        assertEquals(Direction.UP, ofResult.getDestinationDirection());
    }

    @Test
    void testRemoveFinishedFloors6() {
        Elevator ofResult = Elevator.of(3, 10, 1, 1);
        ofResult.addCall(Call.of(10, Direction.DOWN));
        ofResult.addCall(Call.of(10, Direction.UP));
        assertTrue(ofResult.removeFinishedFloors());
        assertEquals(Direction.NONE, ofResult.getDestinationDirection());
    }

}

