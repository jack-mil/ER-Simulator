public class Patient implements Comparable<Patient>{

    private Integer urgency;
    private Double waitTime;

    @Override
    public int compareTo(Patient o) {
        return urgency.compareTo(o.urgency);
    }

}