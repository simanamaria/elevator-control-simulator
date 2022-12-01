package com.coherentsolutions.building;

import com.coherentsolutions.building.enums.Direction;
import com.coherentsolutions.building.helper.Call;
import com.coherentsolutions.building.helper.Controller;
import com.coherentsolutions.person.Person;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@EqualsAndHashCode
public class Floor {
    @Getter
    private final int floorNumber;
    private final Building building;
    private final Queue<Person> queueGoingUp;
    private final Queue<Person> queueGoingDown;

    private Floor(int floorNumber, Building building) {
        checkArgument(floorNumber >= 0);
        checkArgument(floorNumber < building.getNumberOfFloors());
        checkNotNull(building);
        this.floorNumber = floorNumber;
        this.building = building;
        this.queueGoingUp = new ConcurrentLinkedQueue<>();
        this.queueGoingDown = new ConcurrentLinkedQueue<>();
    }

    public static Floor of(int floorNumber, Building building) {
        return new Floor(floorNumber, building);
    }

    public Controller getController() {
        checkNotNull(building.getController());
        return building.getController();
    }

    public void callElevator(Direction direction) {
        checkNotNull(direction);
        checkNotNull(getController());
        getController().addCall(Call.of(floorNumber, direction));
    }

    public void addPerson(Person person) {
        checkNotNull(person);
        Direction direction = person.getCall().getDirection();
        if (direction == Direction.UP) {
            enqueue(queueGoingUp, person);
        } else if (direction == Direction.DOWN) {
            enqueue(queueGoingDown, person);}
        log.info("human has been added to {}", person);
    }

    @Nullable
    public Person getFirstPerson(Direction direction) {
        checkNotNull(direction);
        direction = resolveDirection(direction);
        return direction.equals(Direction.UP) ? queueGoingUp.peek() : queueGoingDown.peek();
    }

    @Nullable
    public Person pollFirstHuman(Direction direction) {
        checkNotNull(direction);
        Person person = null;
        direction = resolveDirection(direction);
        if (getFirstPerson(direction) != null) {
            person = direction.equals(Direction.UP) ? queueGoingUp.poll() : queueGoingDown.poll();
            if (getFirstPerson(direction) != null
                    && getController().canCallElevator(Objects.requireNonNull(getFirstPerson(direction)).getCall())) {
                callElevator(direction);}
            log.info("human has been polled {}", person);}
        return person;
    }

    private void enqueue(Queue<Person> queue, Person person) {
        if (queue.isEmpty()) {person.pushButton();}
        queue.add(person);
    }

    private Direction resolveDirection(Direction direction) {
        if (direction == Direction.NONE) {
            return queueGoingUp.size() > queueGoingDown.size() ? Direction.UP : Direction.DOWN;
        }
        return direction;
    }
}