public class PatientBST {
    private class Node {
        Patient patient;
        Node left;
        Node right;

        Node(Patient patient) {
            this.patient = patient;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public PatientBST() {
        root = null;
    }

    public void insert(Patient patient) {
        root = insertRecursive(root, patient);
    }

    private Node insertRecursive(Node current, Patient patient) {
        if (current == null) {
            return new Node(patient);
        }

        if (patient.getId() < current.patient.getId()) {
            current.left = insertRecursive(current.left, patient);
        } else if (patient.getId() > current.patient.getId()) {
            current.right = insertRecursive(current.right, patient);
        }

        return current;
    }

    public Patient search(int id) {
        return searchRecursive(root, id);
    }

    private Patient searchRecursive(Node current, int id) {
        if (current == null) {
            return null;
        }

        if (id == current.patient.getId()) {
            return current.patient;
        }

        if (id < current.patient.getId()) {
            return searchRecursive(current.left, id);
        } else {
            return searchRecursive(current.right, id);
        }
    }
}