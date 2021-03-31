/**
 * A Patient object represents a patient at the ER. They have a urgency level
 * and arrival time. A new patient will assign itself with a
 * random severity according to the distribution:
 * <ul>
 * <li>1 – 15% minor treatment needed
 * <li>2 – 30% common treatment needed
 * <li>3 – 45% serious treatment needed
 * <li>4 – 10% life or death treatment needed
 * </ul>
 * Patients in a Java PriorityQueue will order themselves by urgency level and
 * arrival time. Level 4 Patiens will always get precedence before others,
 * regardless of arrival time.
 */
public class Patient implements Comparable<Patient> {

	/**
	 * Static count for unique ID.
	 * Unused in simulation, exists for testing purposes
	 */
	private static int count = 0;

	// Instance properties
	private int level, arrivalTime, id;

	/** Create a new patient with random urgency level assignment */
	public Patient(int currentTime) {
		double d = Math.random();
		if (d <= 0.1) {
			level = 4;
		} else if (d <= 0.25) {
			level = 1;
		} else if (d <= 0.55) {
			level = 2;
		} else {
			level = 3;
		}

		arrivalTime = currentTime;
		id = count++;
	}

	/**
	 * Create a new patient with arrival time and severity.
	 * Testing compatability
	 */
	public Patient(int currentTime, int severity) {
		arrivalTime = currentTime;
		level = severity;
		id = count++;
	}

	public int getLevel() {
		return level;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getId() {
		return id;
	}

	/**
	 * The compareTo method as required by the Comparable interface. The
	 * PriorityQueue class uses this method to pick the next item to poll() from the
	 * queue. The object with the highest weight will have priority over others.
	 */
	@Override
	public int compareTo(Patient o) {
		return Double.compare(o.calcWeight(), calcWeight());
	}

	/**
	 * Calculate a weighted sum of urgency level and arrival time.
	 * P1 and P2 weight values chosed specifically to balance the weight around
	 * priority and wait time
	 * @return this Patient's priority weight
	 */
	private double calcWeight() {
		final double P1 = 0.1;
		final double P2 = 1000;
		// Ensure level 4 patients have automatic priority
		if (level == 4) {
			return Integer.MAX_VALUE;
		} else {
			return (P1 * level / 4) + (P2 / arrivalTime);
		}
	}

	/** @return string representation of a Patient. Useful in debugging */
	@Override
	public String toString() {
		return String.format("{id=%d, level=%d, time=%d, weight=%f}", id, level, arrivalTime, calcWeight());
	}
}