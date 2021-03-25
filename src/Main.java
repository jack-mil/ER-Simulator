import java.util.PriorityQueue;
import java.util.Queue;
import java.lang.Math;

// Placeholder
public class Main {

	public static void main(String[] args) {
		double arrival1, arrival2;
		Queue<Patient> waitRoom = new PriorityQueue<>();

		// @TODO Get User Inputs


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
			}
			else if (arrival1 <= 98) { // 2-4 new patients (39%)
				for (int j = 0; j < ((arrival2 % 3) + 2); j++) {
					waitRoom.add(new Patient(i));
				}
			}
			else { // 10-15 new patients (1%)
				for (int j = 0; j < ((arrival2 % 6) + 10); j++) {
					waitRoom.add(new Patient(i));
				}
			}

			// @TODO Create and Implement ER Rooms class for second 1/2 of main loop
		}

		// @TODO Create the Analytics and outputs (Send to a DOC) - Korbin Davis and Nessie Richmond

	}
}
