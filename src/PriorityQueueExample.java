import java.util.PriorityQueue;
import java.util.Queue;

/** Just messing around and testing my Patient methods here.
 * Trying to make sure things are popped in the right order.
 */
public class PriorityQueueExample {
	public static void main(String[] args) {
		Queue<Patient> exampleQueue = new PriorityQueue<>();

		for (int i = 0; i < 100; i++) {
			exampleQueue.add(new Patient((i + 1)));
		}
		System.out.println("Queue Order:");
		int count = 1;
		while (!exampleQueue.isEmpty()) {
			Patient p = exampleQueue.poll();
			int t = p.getArrivalTime();
			System.out.printf("%s diff: %d\n", p, t - count++);
		}
	}
}
