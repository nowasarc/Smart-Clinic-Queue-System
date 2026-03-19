import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ClinicManager {

    private Queue<Patient> regularQueue;
    private PriorityQueue<Patient> priorityQueue;
    private LinkedList<Patient> servedHistory;
    private PatientBST patientBST;

    public ClinicManager() {
        regularQueue = new LinkedList<>();
        priorityQueue = new PriorityQueue<>();
        servedHistory = new LinkedList<>();
        patientBST = new PatientBST();
    }

    public void addPatient(Patient patient) {
        if (patient.isPriority()) {
            priorityQueue.offer(patient);
        } else {
            regularQueue.offer(patient);
        }
        patientBST.insert(patient);
    }

    public Patient serveNextPatient() {
        Patient served;

        if (!priorityQueue.isEmpty()) {
            served = priorityQueue.poll();
        } else if (!regularQueue.isEmpty()) {
            served = regularQueue.poll();
        } else {
            return null;
        }

        servedHistory.add(served);
        return served;
    }

    public Patient searchPatient(int id) {
        return patientBST.search(id);
    }

    public String viewQueues() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== PRIORITY QUEUE ===\n");
        if (priorityQueue.isEmpty()) {
            sb.append("No priority patients.\n");
        } else {
            for (Patient p : priorityQueue) {
                sb.append(p).append("\n");
            }
        }

        sb.append("\n=== REGULAR QUEUE ===\n");
        if (regularQueue.isEmpty()) {
            sb.append("No regular patients.\n");
        } else {
            for (Patient p : regularQueue) {
                sb.append(p).append("\n");
            }
        }

        return sb.toString();
    }

    public String viewHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== SERVED PATIENTS ===\n");

        if (servedHistory.isEmpty()) {
            sb.append("No served patients yet.");
        } else {
            for (Patient p : servedHistory) {
                sb.append(p).append("\n");
            }
        }

        return sb.toString();
    }
}