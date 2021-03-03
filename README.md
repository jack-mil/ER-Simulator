# Group project #
Write a simulation of hospital rooms in use over a 10h time period
#### Keep a timelog of time spent working ####

Program will run in (sped up) real time (1s real time = 1min simulated time)

Arrival must be randomly set based off of the following:
- a. 10% of the time no one comes in
- b. 50% of the time just one person comes in
- c. 39% of the time multiple (2 - 4) people come in at the same time
- d. 1% of the time there is a major incident that 10 to 15 people are brought in

Patient's are assigned urgency rating
1. 15% minor treatment needed
2. 30% common treatment needed
3. 45% serious treatment needed
4. 10% life or death treatment needed

Priority waiting queue
- Urgency 4 gets instant priority

Time requirements:
The room is in use based on urgency distribution
1. 30 min
2. 45 min
3. 60 min
4. 20 min
- 20% chance time is extended (by how much?)

If all rooms are full, even rating 4 must wait

## Questions ##
Can we run faster than 1 second per minute?
Chance time is extended by how much?




# Classes: #

## - `Patient`
- `String name`
- `int priority`
- `int waitTime`
- `public Patient()`
  - Create a new patient object with random rating based on given distribution
