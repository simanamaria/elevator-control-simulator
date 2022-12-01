package com.coherentsolutions.util;

public interface ElevatorInterface {
    int DEFAULT_OPERATION_TIME = 1100;
    int MAX_SPEED = 1000;
    int MIN_SPEED = 1;

    void turnOff();

    void turnOn();
}