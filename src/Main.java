import Controllers.PatientController;
import Models.Medication;
import Models.Patient;
import Models.Prescription;
import View.PrescriptionView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Scanner;

import static Controllers.PatientController.*;

public class Main {
    public static void main(String[] args) {
        final String URL = "jdbc:mysql://127.0.0.1:3306/login_shema";
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            System.out.println("Connexion réussie à la base de données.");
        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }

        PatientController patientController = new PatientController();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            // Affichage du menu principal
            System.out.println("Menu principal :");
            System.out.println("1. Gérer les clients");
            System.out.println("2. Gérer les médicaments");
            System.out.println("3. Gérer les ordonnances");
            System.out.println("4. Gérer les réservations");
            System.out.println("5. Quitter");
            System.out.print("Choisissez une option : ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Menu de gestion des clients
                    manageClients(patientController, scanner);
                    break;
                case 2:
                    // Menu de gestion des médicaments
                    manageMedicines(scanner);
                    break;
                case 3:
                    // Menu de gestion des ordonnances
                    manageOrdonance(scanner);
                    break;
                case 4:
                    // Menu de gestion des réservations
                    manageReservations(scanner);
                    break;
                case 5:
                    // Quitter le programme
                    exit = true;
                    break;
                default:
                    System.out.println("Option invalide. Veuillez choisir à nouveau.");
            }
        }

        scanner.close();
    }

    private static void manageReservations(Scanner scanner) {
    }

    private static void manageOrdonance(Scanner scanner) {

        // Demander les informations sur le patient
        System.out.println("Entrez le nom du patient : ");
        String patientName = scanner.nextLine();

        int patientAge = scanner.nextInt();


        System.out.println("Entrez l'âge du patient : ");
        scanner.nextLine(); // Pour consommer la nouvelle ligne


        System.out.println("Entrez le sexe du patient : ");
        String patientGender = scanner.nextLine();


        System.out.println("Entrez les antécédents médicaux du patient : ");
        String patientMedicalHistory = scanner.nextLine();

        // Créer une instance de patient avec les informations saisies
        Patient patient = new Patient(patientName, patientAge, patientGender, patientMedicalHistory);

        // Demander le nombre de médicaments
        System.out.println("Combien de médicaments souhaitez-vous ajouter à la prescription ?");
        int numberOfMedications = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne
        // Exemple de création de médicaments
        System.out.println("Enter medication name:");
        String name = scanner.nextLine();

        System.out.println("Enter medication dosage:");
        String dosage = scanner.nextLine();

        System.out.println("Enter medication instructions:");
        String instructions = scanner.nextLine();

        Medication medication = new Medication(name, dosage, instructions);

        // Exemple de création d'une prescription
        Prescription prescription = new Prescription(patient, List.of(medication));

        // Affichez l'ordonnance à l'aide de la vue de prescription
        PrescriptionView prescriptionView = new PrescriptionView();
        prescriptionView.printPrescription(prescription);
    }

    private static void manageMedicines(Scanner scanner) {
        
    }

    private static void manageClients(PatientController patientController, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            // Affichage du menu de gestion des clients
            System.out.println("Menu de gestion des clients :");
            System.out.println("1. Afficher tous les clients");
            System.out.println("2. Créer un nouveau client");
            System.out.println("3. Mettre à jour les informations d'un client");
            System.out.println("4. Supprimer un client");
            System.out.println("5. Retour au menu principal");
            System.out.print("Choisissez une option : ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Afficher tous les clients
                    displayAllPatients(patientController);
                    break;
                case 2:
                    // Créer un nouveau client
                    createNewPatient(patientController, scanner);
                    break;
                case 3:
                    // Mettre à jour les informations d'un client
                    updatePatientInfo(patientController, scanner);
                    break;
                case 4:
                    // Supprimer un client
                    deletePatient(patientController, scanner);
                    break;
                case 5:
                    // Retour au menu principal
                    exit = true;
                    break;
                default:
                    System.out.println("Option invalide. Veuillez choisir à nouveau.");
            }
        }
    }

    // Les autres méthodes pour les menus de gestion des médicaments, des ordonnances et des réservations
}
