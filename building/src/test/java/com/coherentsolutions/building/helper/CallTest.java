package com.coherentsolutions.building.helper;

import com.coherentsolutions.building.enums.Direction;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CallTest {

    @Test
    void testOf() {
        Call actualOfResult = Call.of(10, Direction.UP);
        assertEquals(Direction.UP, actualOfResult.getDirection());
    }

    @Test
    void testOf2() {
        try {
            Call.of(0, null);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void testOf3() {
        Call actualOfResult = Call.of(1, Direction.UP);
        assertEquals(Direction.UP, actualOfResult.getDirection());
        assertEquals(1, actualOfResult.getTargetFloorNumber());
    }

    @Test
    void testOf4() {
        assertThrows(IllegalArgumentException.class, () -> Call.of(-1, Direction.UP));
    }

    @Test
    void testOf5() {
        Call actualOfResult = Call.of(10, Direction.DOWN);
        assertEquals(Direction.DOWN, actualOfResult.getDirection());
        assertEquals(10, actualOfResult.getTargetFloorNumber());
    }

    @Test
    void testOf6() {
        Call actualOfResult = Call.of(10, Direction.NONE);
        assertEquals(Direction.NONE, actualOfResult.getDirection());
        assertEquals(10, actualOfResult.getTargetFloorNumber());
    }

}
