package com.coherentsolutions.person;

import com.coherentsolutions.building.Floor;
import com.coherentsolutions.util.BuildingInterface;
import com.coherentsolutions.util.ElevatorInterface;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class PersonTest {

    @Test
    void testOf() {
        try {
            Person.of(3, 10, null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testOf2() {
        try {
            Person.of(0, 0, null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }
}

