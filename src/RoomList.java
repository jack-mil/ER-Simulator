import java.util.Iterator;
import java.util.LinkedList;

/** List of rooms in the ER.
 * Patients admitted to this list will be assigned any available "Room".
 * The amount of time a room remains occupied is determined apon creation based on 
 * Patient urgency level.
 * <ul>
 * <li> 1: 30 min
 * <li> 2: 45 min
 * <li> 3: 60 min
 * <li> 4: 20 min
 * </ul>
 * There is an additional 20% chance the time is extended by to 1/2 of base time.
*/
public class RoomList {

	/** Maximum allowable rooms */
	private int maxRooms;

	/** Records for greatest ever size */
	private int maxEverOccupied;

	/** Internal list used to store and iterate over the Room objects */
	private LinkedList<Room> roomsLL;

	/** Construct an empy Room List */
	public RoomList(int maxRooms) {
		this.maxRooms = maxRooms;
		// A LinkedList is used because elements will be added and removed from arbitrary points frequently
		roomsLL = new LinkedList<>();
	}

	/**
	 * Add a new patient to any open "room".
	 * Will fail to add a patient if all rooms are full.
	 * Inserts a new Room node to the end of the chain.
	 * Implementation straight from java.util.LinkedList.
	 * @param p Patient to be added
	 * @return true if the addition was succesful, false otherwise
	 */
	public boolean admit(Patient p, int currentTime) {
		// Return false and ignore patient if no more rooms are available
		if (roomsLL.size() >= maxRooms) {
			return false;
		} else {
			roomsLL.add(new Room(p, currentTime));
			return true;
		}

	}

	/** Everytime this method is called, empty rooms will become available
	 * if enough time has passed
	 * @return number of room openings
	 */
	public int tick(int currentTime) {
		// Use an iterator to loop through the linked list and remove Rooms that are old
		// This can all be accomplished in linear time O(n) because of the LinkedList implementation
		Iterator<Room> itr = roomsLL.iterator();

		int count = 0;
		while(itr.hasNext()){
			Room r = itr.next();
			// Check if the room has been occupied long enough
			if(currentTime - r.timeIn >= r.useTime) {
				// Remove the last room returned by next() from the linked list.
				// This should occur in O(1) time.
				itr.remove();
				count++;
			}
		}

		return count;
	}
	/** @return number of rooms currently occupied */
	public int getOccupiedRooms() {
		return roomsLL.size();
	}

	/** @return true if space is available */
	public boolean hasEmptyRooms() {
		return (roomsLL.size() < maxRooms);
	}

	/** @return greatest number of rooms ever occupied at one time */
	public int getMaxEverOccupancy() {
		if (roomsLL.size() > maxEverOccupied)
			maxEverOccupied = roomsLL.size();

		return maxEverOccupied;
	}

	/** @return smallest number of rooms ever left vacant (surplus) */
	public int getMinEverVacancy() {
		return maxRooms - getMaxEverOccupancy();
	}

	/** Internal node like class
	 * Rooms assign themselves the correct occupancy time based on patient level
	*/
	private static class Room {
		// Is the patient information even needed anymore?
		// Patient patient;

		int timeIn;
		int useTime;

		/** Create a new room object that contains a patient and a time admited */
		Room(Patient patient, int timeIn) {
			//@TODO implement random chance to increase base in-use time
			switch (patient.getLevel()) {
			case 1:
				useTime = 30;
				break;
			case 2:
				useTime = 45;
			case 3:
				useTime = 60;
			default: // Default case is urgency 4
				useTime = 20;
				break;
			}
			// this.patient = patient;
			this.timeIn = timeIn;
		}
	}

}