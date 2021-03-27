import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.lang.Math;

// Placeholder
public class Main {

	/** Maximum allowable rooms */
	public static int maxRooms;

	/** Total patient counts */
	public static int patientCount, admittedCount;

	public static void main(String[] args) {

		/** Random Variable Placeholders */
		double arrival1, arrival2;

		/** Instantiate a new Scanner Object for User Input */
		Scanner sc = new Scanner(System.in);

		// @TODO Get User Inputs

		maxRooms = sc.nextInt();

		/** Construct an empty Waiting Room Priority Queue */
		Queue<Patient> waitRoom = new PriorityQueue<>();

		/** Construct an empty ER Rooms RoomList (modified Linked List) */
		RoomList eRooms = new RoomList(maxRooms);

		// @TODO Create Main Simulation loop - Korbin Davis
		for (int i = 1; i < 601; i++) { // Counting from 1 to 600 so that we just have seconds
			// Instantiate the patient objects directly into the queue
			arrival1 = (Math.floor(Math.random() * 100.0) % 100); // Random variable 0-99
			arrival2 = (Math.floor(Math.random() * 100.0) % 100); // Random variable 0-99
			if (arrival1 <= 9) { // No new patients (10%)
				continue;
			}
			else if (arrival1 <= 59) { // 1 new patient (50%)
				waitRoom.add(new Patient(i));
				patientCount++;
			}
			else if (arrival1 <= 98) { // 2-4 new patients (39%)
				for (int j = 0; j < ((arrival2 % 3) + 2); j++) {
					waitRoom.add(new Patient(i));
					patientCount++;
				}
			}
			else { // 10-15 new patients (1%)
				for (int j = 0; j < ((arrival2 % 6) + 10); j++) {
					waitRoom.add(new Patient(i));
					patientCount++;
				}
			}

			// @TODO Create and Implement ER Rooms class for second 1/2 of main loop
			while (eRooms.hasEmptyRooms() == true && waitRoom.isEmpty() == false) {
				// Remove the next Patient from the waiting room and
				// Put them in an empty ER Room
				eRooms.admit(waitRoom.poll(), i);
				admittedCount++;
			}

			eRooms.tick(i);
		}

		// @TODO Create the Analytics and outputs (Send to a DOC) - Korbin Davis and Nessie Richmond

		// @TODO Tie up loose ends
		sc.close();
	}
}
