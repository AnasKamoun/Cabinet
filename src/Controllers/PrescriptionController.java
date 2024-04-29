package Controllers;

import Models.Medication;
import Models.Patient;
import Models.Prescription;
import View.PrescriptionView;

import java.util.List;

public class PrescriptionController {
    private PrescriptionView view;

    public PrescriptionController(PrescriptionView view) {
        this.view = view;
    }

    public void createAndPrintPrescription(String patientName, int patientAge, String patientGender, String patientMedicalHistory, Medication[] medications) {
        // Créez une instance de patient
        Patient patient = new Patient(patientName, patientAge, patientGender, patientMedicalHistory);

        // Créez une instance de prescription avec le patient et les médicaments
        List<Medication> Medications = List.of();
        Prescription prescription = new Prescription(patient, Medications);

        // Affichez l'ordonnance à l'aide de la vue de prescription
        view.printPrescription(prescription);
    }
}
