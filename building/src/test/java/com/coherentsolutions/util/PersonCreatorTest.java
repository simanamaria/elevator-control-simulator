package com.coherentsolutions.util;

import com.coherentsolutions.building.Building;
import com.coherentsolutions.building.Elevator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonCreatorTest {

    @Test
    void testOf2() {
        try {
            PersonCreator.of(null, 0, 200, 1);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testOf3() {
        try {
            PersonCreator.of(Building.of(10), 1000, 3, 1);
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    void testOf4() {
        try {
            PersonCreator.of(Building.of(10), -1, 3, 1);
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    void testOf5() {
        try {
            PersonCreator.of(Building.of(10), 1, 1000, 1);
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    void testOf6() {
        try {
            PersonCreator.of(Building.of(10), 1, 3, 0);
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    void testCreateHuman() {
        try {
            PersonCreator.of(Building.of(10), 1, 3, 1).createHuman();
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        } catch (NullPointerException e){
            assertTrue(e instanceof  NullPointerException);
        }
    }

    @Test
    void testRun2() {
        Building ofResult = Building.of(10);
        try {
            ofResult.addElevator(Elevator.of(3, 10, 1, 1));
            PersonCreator.of(ofResult, 1, 3, 1).run();
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        } catch (NullPointerException e){
            assertTrue(e instanceof NullPointerException);
        }

    }
}

