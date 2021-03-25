++++++++++++++++++++++++++++++++++++++++++++++++++++++# Group project #
Write a simulation of hospital rooms in use over a 10h time period
#### [Work timelog](Docs/TimeLog.md) ####
------------------------------------------
#### [Code Style Guide](Docs/Style.md) ####
------------------------------------------
#### Specifications ####
Program will run in (sped up) real time (1s real time = 1min simulated time).
We should request to run the simulation as fast as possible (600 simulation steps/iterations).

User picks an estimate of number of rooms needed. Analysis states whether that amount was sufficient.
Number of rooms left empty, patient levels left waiting (average time perhaps?).

Sampled rate up to us. Perhaps use 10% of 600 min is 60, %50 is 300, etc?

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
- 20% chance time is extended by 5 to 1/2 of base for every patient (failed constitution saving throw)

If all rooms are full, even rating 4 must wait

End: How many people (and priority) left in waiting room? How many came in? Average waiting time?
Max and Min rooms empty during simulation?

## Questions ##
Can we run faster than 1 second per minute?

Output all at end or throughout? (Maybe put details to a log file, general updates and final analysis to std.in?)

# Classes: #

### `Patient` ###
- see [Patient Docs](Docs/AlgorithmDesign-Jackson.md)
