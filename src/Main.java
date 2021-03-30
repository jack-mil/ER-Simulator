import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.lang.Math;
/**
 * Main simulation class
 * Run a 10h simulation of emergency room patient logistics.
 * See README and docs for detailed explanations
 *
 * @author Korbin Davis, Jackson Miller, Nessie Richmond
 */
public class Main {

	/** Maximum allowable rooms */
	static int maxRooms;

	/** Total patient counts */
	static int arrivalCount;
	static int admittedCount;

	/** Cumulative Moving Average of Waiting Time */
	static double avgWait;

	/** The queue of arriving patients */
	static Queue<Patient> waitRoom;

	public static void main(String[] args) {

		// Scanner object for user input
		Scanner sc = new Scanner(System.in);

		// Get simulation parameters
		System.out.print("Enter number of emergency rooms: ");
		maxRooms = sc.nextInt();

		System.out.print("Enter period between patient arrivals in minutes e.g. 1,5,10: ");
		int period = sc.nextInt();

		sc.close();

		// Construct an empty ER Rooms RoomList (Linked List wrapper)
		RoomList eRooms = new RoomList(maxRooms);

		// Construct an empty Waiting Room Priority Queue
		waitRoom = new PriorityQueue<Patient>();

		System.out.println("Begin 10h simulation:\n...");

		// Main Simulation loop - Korbin Davis
		// Each iteration represents a minute
		// 600 minutes = 10h
		for (int i = 1; i <= 600; i++) {

			// Every interval, potentially generate new patients
			if ((i-1) % period == 0) {
				rollForPatients(i);
			}

			// Let as many patients in queue as possible into available rooms
			while (eRooms.hasEmptyRooms() && !waitRoom.isEmpty()) {
				// Remove the next Patient from the waiting room and
				// put them in an empty ER Room
				// Also records their waiting time for the Cumulative Average
				Patient p = waitRoom.poll();
				avgWait = ((i - p.getArrivalTime()) + admittedCount * avgWait) / (admittedCount + 1);
				eRooms.admit(p, i);
				admittedCount++;
			}

			// Update the rooms list with the current time to open up any available rooms
			eRooms.tick(i);
		}

		// Print out simulation statistics
		int max = eRooms.getMaxEverOccupancy();
		int min = eRooms.getMinEverVacancy();
		System.out.print("Total patients arrived:	%d\n".formatted(arrivalCount)
				+ "Total patients treated:	%d\n".formatted(admittedCount)
				+ "Patients left in wating room:	%d\n".formatted(waitRoom.size()) + formatRemainingPatients().indent(2)
				+ "Average wait time:	%.1f min\n".formatted(avgWait) + "\n"
				+ "Max room occupancy:	%d/%d\n".formatted(max, maxRooms) + "Unused Rooms:	%d/%d\n".formatted(min, maxRooms));

		// @TODO Create the Analytics and outputs (Send to a DOC) - Korbin Davis and Nessie Richmond

	}

	/**
	 * Create a varying amount of new patients based on a random distribution
	 * see README
	 * After calling this method, waitRoom queue contains the new patients
	 * @param i current time / simulation step
	 * @author Korbin Davis
	 */
	private static void rollForPatients(int i) {
		// Instantiate the patient objects directly into the queue
		double arrival1 = (Math.floor(Math.random() * 100.0) % 100); // Random variable 0-99
		double arrival2 = (Math.floor(Math.random() * 100.0) % 100); // Random variable 0-99
		if (arrival1 <= 9) { // No new patients (10%)
			return;
		} else if (arrival1 <= 59) { // 1 new patient (50%)
			waitRoom.add(new Patient(i));
			arrivalCount++;
		} else if (arrival1 <= 98) { // 2-4 new patients (39%)
			for (int j = 0; j < ((arrival2 % 3) + 2); j++) {
				waitRoom.add(new Patient(i));
				arrivalCount++;
			}
		} else { // 10-15 new patients (1%)
			for (int j = 0; j < ((arrival2 % 6) + 10); j++) {
				waitRoom.add(new Patient(i));
				arrivalCount++;
			}
		}
	}

	/** Simply return a formatted string of patients left in the queue */
	public static String formatRemainingPatients() {
		int[] lvs = new int[4];
		for (Patient p : waitRoom) {
			lvs[p.getLevel() - 1]++;
		}
		String msg =
		"Urgency	Count\n"
		+ "1		%d\n"
		+ "2		%d\n"
		+ "3		%d\n"
		+ "4		%d\n";
		return msg.formatted(lvs[0], lvs[1], lvs[2], lvs[3]);
	}
}
