package com.coherentsolutions.building.helper;

import com.coherentsolutions.building.enums.Direction;
import lombok.Getter;
import lombok.EqualsAndHashCode;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Getter
@EqualsAndHashCode
public class Call {
    private final int targetFloorNumber;
    private final Direction direction;

    private Call(int targetFloorNumber, Direction direction) {
        checkArgument(targetFloorNumber >= 0);
        checkNotNull(direction);
        this.targetFloorNumber = targetFloorNumber;
        this.direction = direction;
    }
    public static Call of(int targetFloorNumber, Direction direction){
        if (direction == null) {throw new NullPointerException();}
        if (targetFloorNumber < 0){throw new IllegalArgumentException();}
        return new Call(targetFloorNumber, direction);
    }

    @Override
    public String toString() {
        return String.format("(To->%S; Direction:%s)", targetFloorNumber, direction);
    }
}