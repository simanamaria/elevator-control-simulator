package com.coherentsolutions.util;

import com.coherentsolutions.building.Building;
import com.coherentsolutions.building.enums.Direction;
import com.coherentsolutions.building.helper.Call;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingInterfaceTest {


    @Test
    void testOf() {
        try {
            BuildingInterface.of(null, ElevatorInterface.MAX_SPEED);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testOf2() {
        try {
            BuildingInterface.of(Building.of(10), 0);
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }
}

