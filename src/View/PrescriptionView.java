package View;

import Models.Medication;
import Models.Patient;
import Models.Prescription;

import java.util.List;

public class PrescriptionView {
    public void printPrescription(Prescription prescription) {
        Patient patient = prescription.getPatient();
        System.out.println("Patient: " + patient.getName());
        System.out.println("Age: " + patient.getAge());
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Medical History: " + patient.getMedicalHistory());

        System.out.println("Medications:");
        List<Medication> medications = prescription.getMedications();
        for (Medication med : medications) {
            System.out.println("Name: " + med.getName());
            System.out.println("Dosage: " + med.getDosage());
            System.out.println("Instructions: " + med.getInstructions());
            System.out.println();
        }
    }
}
