package Models;

import java.util.List;

public class Prescription {
    private Patient patient;
    private List<Medication> medications;

    public Prescription(Patient patient, List<Medication> medications) {
        this.patient = patient;
        this.medications = medications;
    }

    public Patient getPatient() {
        return patient;
    }

    public List<Medication> getMedications() {
        return medications;
    }
}
