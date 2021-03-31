import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.lang.Math;
import java.io.FileOutputStream;
import java.io.IOException;
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
	static int[] arrivalCount = new int[5];
	static int[] admittedCount = new int[5];

	/** Cumulative Moving Average of Waiting Time */
	static double[] avgWait = new double[5];

	/** The queue of arriving patients */
	static Queue<Patient> waitRoom;

	/** The current working directory */
	static String currentDirectory = System.getProperty("user.dir");

	public static void main(String[] args) throws IOException {

		//variables for user input
		int period = 0;

		// Scanner object for user input
		Scanner sc = new Scanner(System.in);

		//Ask user for room quanitity and patient arrival frequency
		System.out.println("Welcome to the Emergency Room Simulator!");
		System.out.println("Please enter your best numerical estimate for the number of rooms filled");
		maxRooms = sc.nextInt();
		System.out.print("Enter period between potential patient arrivals, in minutes, e.g. 1,5,10: ");
		period = sc.nextInt();
		System.out.println("Thanks! The simulation will now begin.");

		sc.close();

		// Construct an empty ER Rooms RoomList (Linked List wrapper)
		RoomList eRooms = new RoomList(maxRooms);

		// Construct an empty Waiting Room Priority Queue
		waitRoom = new PriorityQueue<Patient>();

		// Pretend to take a little while to calculate (for improved user experience)
		System.out.print("Begin 10h simulation:\n");
		try {
			for (int i = 0; i < 3; i++) {
				Thread.sleep(600);
				System.out.print(".");
			}
			System.out.print("\n");
			Thread.sleep(600);	
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

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
				int level = p.getLevel();
				avgWait[0] = ((i - p.getArrivalTime()) + admittedCount[0] * avgWait[0]) / (admittedCount[0] + 1);
				avgWait[level] = ((i - p.getArrivalTime()) + admittedCount[level] * avgWait[level]) / (admittedCount[level] + 1);
				eRooms.admit(p, i);
				admittedCount[0]++;
				admittedCount[level]++;
			}

			// Update the rooms list with the current time to open up any available rooms
			eRooms.tick(i);
		}

		// Print out simulation statistics
		int max = eRooms.getMaxEverOccupancy();
		int min = eRooms.getMinEverVacancy();
		String data =
			"Simulation report for %d rooms and %d min arrival period\n".formatted(maxRooms, period)
			+ formatTotals() + "\n"
			+ formatRemaining() + "\n"
			+ "Total Average wait time:	%.1f min\n".formatted(avgWait[0])
			+ "Average wait time for level 1:	%.1f min\n".formatted(avgWait[1])
			+ "Average wait time for level 2:	%.1f min\n".formatted(avgWait[2])
			+ "Average wait time for level 3:	%.1f min\n".formatted(avgWait[3])
			+ "Average wait time for level 4:	%.1f min\n".formatted(avgWait[4])
			+ "Max room occupancy:	%d/%d\n".formatted(max, maxRooms)
			+ "Unused Rooms:	%d/%d\n".formatted(min, maxRooms);

		FileOutputStream out = new FileOutputStream("ER_Rooms_Simulation_Output.txt");
		out.write(data.getBytes());
		out.close();

		System.out.println(data);

		System.out.print("\nThe output file is: " + currentDirectory + "\\ER_Rooms_Simulation_Output.txt"
				+ "\n" +"Have a great day!!!");
	}

	/**
	 * Create a varying amount of new patients based on a random distribution
	 * see README
	 * After calling this method, waitRoom queue contains the new patients
	 * @param i current time / simulation step
	 */
	private static void rollForPatients(int i) {
		// Instantiate the patient objects directly into the queue
		double arrival1 = (Math.floor(Math.random() * 100.0) % 100); // Random variable 0-99
		double arrival2 = (Math.floor(Math.random() * 100.0) % 100); // Random variable 0-99
		if (arrival1 <= 9) { // No new patients (10%)
			return;
		} else if (arrival1 <= 59) { // 1 new patient (50%)
			waitRoom.add(createPatient(i));
		} else if (arrival1 <= 98) { // 2-4 new patients (39%)
			for (int j = 0; j < ((arrival2 % 3) + 2); j++) {
				waitRoom.add(createPatient(i));
			}
		} else { // 10-15 new patients (1%)
			for (int j = 0; j < ((arrival2 % 6) + 10); j++) {
				waitRoom.add(createPatient(i));
			}
		}
	}

	/** Helper method to spawn patients */
	private static Patient createPatient(int i) {
		Patient p = new Patient(i);
		// Increment total patient count
		arrivalCount[0]++;
		// Increment count for this patient level
		arrivalCount[p.getLevel()]++;
		return p;
	}

	/** Return formatted string of patients left in the queue */
	private static String formatRemaining() {
		int[] lvs = new int[4];
		for (Patient p : waitRoom) {
			lvs[p.getLevel() - 1]++;
		}
		String msg =
		"Patients left in waiting room:	%d\n".formatted(waitRoom.size())
		+ "Urgency		Count\n"
		+ ("1.		%d\n"
		+ "2.		%d\n"
		+ "3.		%d\n"
		+ "4.		%d\n").indent(2);
		return msg.formatted(lvs[0], lvs[1], lvs[2], lvs[3]);
	}

	/** Return formatted string on patient totals */
	private static String formatTotals() {
		String msg = "Total patients arrived: %d\n".formatted(arrivalCount[0])
		+ "Total patients treated: %d\n".formatted(admittedCount[0])
		+ "Urgency		%total\n"
		+ ("1.		%.1f%%\n".formatted(arrivalCount[1] / (double)arrivalCount[0] * 100)
		+ "2.		%.1f%%\n".formatted(arrivalCount[2] / (double)arrivalCount[0] * 100)
		+ "3.		%.1f%%\n".formatted(arrivalCount[3] / (double)arrivalCount[0] * 100)
		+ "4.		%.1f%%\n".formatted(arrivalCount[4] / (double)arrivalCount[0] * 100)).indent(2)
		+ "Urgency		%treated\n"
		+ ("1.		%.1f%%\n".formatted(admittedCount[1] / (double)arrivalCount[1] * 100)
		+ "2.		%.1f%%\n".formatted(admittedCount[2] / (double)arrivalCount[2] * 100)
		+ "3.		%.1f%%\n".formatted(admittedCount[3] / (double)arrivalCount[3] * 100)
		+ "4.		%.1f%%\n".formatted(admittedCount[4] / (double)arrivalCount[4] * 100)).indent(2);
		return msg;
	}
}
