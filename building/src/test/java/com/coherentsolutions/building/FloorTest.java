package com.coherentsolutions.building;

import com.coherentsolutions.building.enums.Direction;
import com.coherentsolutions.building.helper.Controller;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {

    @Test
    void testOf() {
        try {
            Floor.of(10, Building.of(10));
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    void testOf2() {
        try {
            Floor.of(0, null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testOf3() {
        assertEquals(1, Floor.of(1, Building.of(10)).getFloorNumber());
    }

    @Test
    void testOf4() {
        try {
            Floor.of(-1, Building.of(10));
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    void testGetController() {
        try {
            Floor.of(1, Building.of(10)).getController();
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testGetController2() {
        Building ofResult = Building.of(10);
        Controller empty = Controller.getEmpty();
        ofResult.setController(empty);
        assertSame(empty, Floor.of(1, ofResult).getController());
    }

    @Test
    void testCallElevator() {
        try {
            Floor.of(1, Building.of(10)).callElevator(Direction.UP);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testCallElevator2() {
        try {
            Floor.of(1, Building.of(10)).callElevator(null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testAddPerson() {
        try {
            Floor.of(1, Building.of(10)).addPerson(null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testGetFirstPerson() {
        assertNull(Floor.of(1, Building.of(10)).getFirstPerson(Direction.UP));
        assertNull(Floor.of(1, Building.of(10)).getFirstPerson(Direction.DOWN));
        assertNull(Floor.of(1, Building.of(10)).getFirstPerson(Direction.NONE));
    }

    @Test
    void testGetFirstPerson2() {
        try {
            Floor.of(1, Building.of(10)).getFirstPerson(null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testPollFirstHuman() {
        assertNull(Floor.of(1, Building.of(10)).pollFirstHuman(Direction.UP));
        assertNull(Floor.of(1, Building.of(10)).pollFirstHuman(Direction.DOWN));
        assertNull(Floor.of(1, Building.of(10)).pollFirstHuman(Direction.NONE));
    }

    @Test
    void testPollFirstHuman2() {
        try {
            Floor.of(1, Building.of(10)).pollFirstHuman(null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }
}

