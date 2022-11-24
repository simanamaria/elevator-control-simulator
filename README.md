# Elevators
## Side Project
1. There is a multi-floor building (the number of floors is configurable). The building has elevators (the number is configurable). On each floor, there are call buttons “up” and “down” (typical for all elevators). On each floor, people appear (random mass) who want to go to another floor (random). The intensity of the generation of people is configurable on a floor basis.
2. Each elevator has a load capacity, speed, and door opening/closing speed.
3. A person has a mass and a floor to which he needs.
4. People stand in line to board the elevators (one line up, one line down) without disturbing it. Arriving at the desired floor, the person disappears.
**Task.** It is necessary to implement a continuously running application (people appear, call the elevator and go to the desired floor) using multithreading (Thread, wait, notify, sleep).
You can optionally use `java.util.concurrent`. Also, describe the selected algorithm in a text (briefly).
- tests, maven, logging;
- implement the collection of statistics (how many people were transported by each elevator, from which floors, and to which floors);
- log the main events of the system (so that you can follow what is happening in the logs);
- recommendation: implement the elevator control logic, cover it with tests without threads and enable multithreading.
