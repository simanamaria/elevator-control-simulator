package com.coherentsolutions.consoleApp;


import com.coherentsolutions.building.Building;
import com.coherentsolutions.building.helper.Controller;
import com.coherentsolutions.building.Elevator;
import com.coherentsolutions.util.PersonCreator;
import com.coherentsolutions.util.BuildingInterface;

public class elevatorApp {
    public static void main(String[] args){
        int numberOfFloors = 10;
        int capacityOfElevator = 10;
        int startFloorNumber = 0;
        int movingSpeed = 10;
        int doorSpeed = 10;
        int personSpeed = 10;
        int UISpeed = 600;

        Building building = Building.of(numberOfFloors)
                .setController(Controller.getEmpty())
                .addElevator(Elevator.of(capacityOfElevator, startFloorNumber, movingSpeed, doorSpeed))
                .addElevator(Elevator.of(capacityOfElevator, startFloorNumber, movingSpeed, doorSpeed))
                .addElevator(Elevator.of(capacityOfElevator, startFloorNumber, movingSpeed, doorSpeed));
        PersonCreator PersonCreator = com.coherentsolutions.util.PersonCreator.of(building,
                0, 200, personSpeed);

        BuildingInterface ui = BuildingInterface.of(building, UISpeed);

        PersonCreator.start();
        ui.start();
        building.start();
    }}