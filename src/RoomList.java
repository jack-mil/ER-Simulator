import java.util.Iterator;
import java.util.LinkedList;

/**
 * List of rooms in the ER.
 * Patients admitted to this list will be assigned any available "Room".
 * The amount of time a room remains occupied is determined on 
 * creation based on Patient urgency level.
 * <ul>
 * <li> 1: 30 min
 * <li> 2: 45 min
 * <li> 3: 60 min
 * <li> 4: 20 min
 * </ul>
 * There is an additional 20% chance the time is extended by up to 1/2 of base time.
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
	 * @param p Patient to be added
	 * @return true if the addition was succesful, false otherwise
	 */
	public boolean admit(Patient p, int currentTime) {
		// Return false and ignore patient if no more rooms are available
		if (roomsLL.size() >= maxRooms) {
			return false;
		} else {
			roomsLL.add(new Room(p, currentTime));
			// update largest size
			if (roomsLL.size() >= maxEverOccupied)
				maxEverOccupied = roomsLL.size();

			return true;
		}
	}

/**
 * Every time this method is called, empty rooms will become
 * available if enough time has passed
 * @return number of room openings
 */
public int tick(int currentTime) {
	// Use an iterator to loop through the linked list and remove Rooms that are old
	// This can all be accomplished in linear time because of the LinkedList implementation
	Iterator<Room> itr = roomsLL.iterator();

	int count = 0;
	while (itr.hasNext()) {
		Room r = itr.next();
		// Check if the room has been occupied long enough
		if (currentTime - r.timeIn >= r.occupiedTime) {
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
		return maxEverOccupied;
	}

	/** @return smallest number of rooms ever left vacant (surplus) */
	public int getMinEverVacancy() {
		return maxRooms - getMaxEverOccupancy();
	}

	/**
	 * Internal data class
	 * Rooms assign themselves the correct occupancy time based on patient level
	*/
	private static class Room {
		/** The time this room became occupied */
		int timeIn;
		/** Total time this room will remain occupied */
		int occupiedTime;

		/** Create a new room object with data based on patient level
		 * @author Korbin Davis
		*/
		Room(Patient patient, int timeIn) {
			// Randomly assigns patient treatment time
			double timeVar1 = Math.random();
			int timeVar2 = (int) (Math.floor(Math.random() * 100.0) % 100);

			switch (patient.getLevel()) {
			case 1:
				occupiedTime = timeVar1 > 0.2 ? 30 : 35 + timeVar2 % 11;
				break;
			case 2:
				// Since Time is an integer, I rounded 45/2 up to 23.
				occupiedTime = timeVar1 > 0.2 ? 45 : 50 + timeVar2 % 19;
				break;
			case 3:
				occupiedTime = timeVar1 > 0.2 ? 60 : 65 + timeVar2 % 26;
				break;
			default: // Default case is urgency 4
				occupiedTime = timeVar1 > 0.2 ? 20 : 25 + timeVar2 % 6;
				break;
			}
			this.timeIn = timeIn;
		}

		/** @return String representation of the room data */
		public String toString() {
			return String.format("{timeIn: %d, duration: %d}", timeIn, occupiedTime);
		}
	}

}