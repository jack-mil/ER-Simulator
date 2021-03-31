# COSC2203 Data Structures Project #
![Artwork by Nessie](Docs/artwork-small.jpg) <!-- .element height="20%" width="20%" -->
A simulation of hospital rooms in use over a 10h time period
#### [Work time log](Docs/TimeLog.md) ####
------------------------------------------
#### [Code Style Guide](Docs/Style.md) ####
------------------------------------------
#### Overview ####
##### :warning: Java Version 15 or up required #####
User picks an estimate of number of rooms needed. Analysis states whether that amount was sufficient.
Data includes:
- Number of rooms used/unused
- Urgency of patients left waiting
- Average wait time

How often patients could potentially arrive is configurable

Arrival is randomly set based off of the following:
- 10% of the time no one comes in
- 50% of the time just one person comes in
- 39% of the time multiple (2 - 4) people come in at the same time
- 1% of the time there is a major incident that 10 to 15 people are brought in

Patient's are assigned an urgency rating
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

[See Example Output](Example-Output.txt)
## Classes: ##

### `Patient` ###
- see [Patient Docs](Docs/Patient.md)
### `RoomList` ###
- see [RoomList Docs](Docs/RoomList.md)
