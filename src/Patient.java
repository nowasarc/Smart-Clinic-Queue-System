public class Patient implements Comparable<Patient> {
    private int id;
    private String name;
    private String service;
    private boolean priority;
    private int priorityLevel;

    public Patient(int id, String name, String service, boolean priority, int priorityLevel) {
        this.id = id;
        this.name = name;
        this.service = service;
        this.priority = priority;
        this.priorityLevel = priorityLevel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getService() {
        return service;
    }

    public boolean isPriority() {
        return priority;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    @Override
    public int compareTo(Patient other) {
        return Integer.compare(this.priorityLevel, other.priorityLevel);
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Name: " + name +
                " | Service: " + service +
                " | Priority: " + (priority ? "Yes (" + priorityLevel + ")" : "No");
    }
}