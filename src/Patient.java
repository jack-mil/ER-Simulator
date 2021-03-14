/**
 * A Patient object represents a patient at the ER. They have a urgency level
 * and arrival time, as well as a unique id (currently redundant). A new patient
 * will assign itself with a random severity according to the distribution:
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

	// Static count of how many Patients exists. Used for unique id.
	private static int count = 0;

	// Instance properties
	private int level;
	private int arrivalTime;
	private int id;

	/** Create a new patient with random urgency level assignment */
	public Patient(int currentTime) {
		double d = Math.random();
		if (d <= 0.1) {
			level = 4;
		} else if (d <= 0.15) {
			level = 1;
		} else if (d <= 0.3) {
			level = 2;
		} else {
			level = 3;
		}

		arrivalTime = currentTime;
		id = count++;
	}

	/**
	 * Create a new patient with arrival time and severity. May be useful in testing
	 */
	public Patient(int currentTime, int severity) {
		arrivalTime = currentTime;
		level = severity;
		id = count++;
	}

	int getLevel() {
		if (level == 4)
			return 100;
		return level;
	}

	int getArrivalTime() {
		return arrivalTime;
	}

	int getId() {
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
	 * Temporary implementation, will probably be tweaked after testing.
	 * Currently the equation values level over arrival time.
	 * 
	 * @return
	 */
	private double calcWeight() {
		final double P1 = 0.6;
		final double P2 = 0.4;
		return (P1 * getLevel() / 4) + (P2 * 1 / arrivalTime);
	}

	/**
	 * The equals method wich may be needed
	 * //TODO: test this method if needed
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if ((o == null) || getClass() != o.getClass())
			return false;
		Patient p = (Patient) o;
		return (p.id == id && p.level == level && p.arrivalTime == arrivalTime);
	}

	/**
	 * Return a string representation of a Patient. Useful in debugging
	 * System.out.println(new Patient(30));
	 */
	@Override
	public String toString() {
		return String.format("Patient{id=%d, level=%d, time=%d, weight=%f}", id, level, arrivalTime, calcWeight());
	}
}