package com.coherentsolutions.person;

import com.coherentsolutions.building.helper.Call;
import com.coherentsolutions.building.Floor;
import com.coherentsolutions.building.enums.Direction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Getter
@ToString
@EqualsAndHashCode
public class Person {
    private final int weight;
    private final Call call;
    private final Floor startFloor;

    private Person(int weight, int targetFloor, Floor startFloor) {
        checkNotNull(startFloor);
        checkArgument(targetFloor >= 0);
        checkArgument(targetFloor != startFloor.getFloorNumber());
        checkArgument(weight >= 0 && weight <= 200);

        this.startFloor = startFloor;
        this.weight = weight;
        this.call = Call.of(targetFloor,
                targetFloor - startFloor.getFloorNumber() > 0 ? Direction.UP : Direction.DOWN);
    }

    public static Person of(int weight, int targetFloorNumber, Floor startFloor) {
        return new Person(weight, targetFloorNumber, startFloor);
    }

    public void pushButton() {
        startFloor.callElevator(call.getDirection());
    }
}