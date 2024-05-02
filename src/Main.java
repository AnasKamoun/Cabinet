import Controllers.MedicationController;
import Controllers.OrdenanceController;
import Controllers.PatientController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

import static Controllers.MedicationController.*;
import static Controllers.PatientController.*;

public class Main {
    public static void main(String[] args) {
        final String URL = "jdbc:mysql://127.0.0.1:3306/login_shema2";
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            System.out.println("Connexion réussie à la base de données.");
        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
        MedicationController MedicationController = new MedicationController();
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
                    manageMedications(MedicationController,scanner);
                    break;
                case 3:
                    // Menu de gestion des ordonnances

                    manageOrdonance();
                    break;
                case 4:
                    // Menu de gestion des réservations
                    /*manageReservations(scanner);*/
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
    public static void manageOrdonance() {
        Scanner scanner = new Scanner(System.in);
        OrdenanceController ordenanceController = new OrdenanceController();

        boolean exit = false;
        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("1. Create New Ordenance");
            System.out.println("2. Display All Ordenances");
            System.out.println("3. Update Ordenance");
            System.out.println("4. Delete Ordenance");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    OrdenanceController.createNewOrdenance(ordenanceController, scanner);
                    break;
                case 2:
                    OrdenanceController.displayAllOrdenances(ordenanceController);
                    break;
                case 3:
                    OrdenanceController.updateOrdenance(ordenanceController, scanner);
                    break;
                case 4:
                    OrdenanceController.deleteOrdenance(ordenanceController, scanner);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }

        scanner.close();
        System.out.println("Program exited.");
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

    private static void manageMedications(MedicationController medicationController, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Medication Management Menu:");
            System.out.println("1. Display all medications");
            System.out.println("2. Add a new medication");
            System.out.println("3. Update medication information");
            System.out.println("4. Delete a medication");
            System.out.println("5. Return to main menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // To consume the leftover newline character

            switch (choice) {
                case 1:
                    // Display all medications
                    displayAllMedications(medicationController);
                    break;
                case 2:
                    // Add a new medication
                    createNewMedication(medicationController, scanner);
                    break;
                case 3:
                    // Update medication information
                    updateMedication(medicationController, scanner);
                    break;
                case 4:
                    // Delete a medication
                    deleteMedication(medicationController, scanner);
                    break;
                case 5:
                    // Return to main menu
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }


    // Les autres méthodes pour les menus de gestion des médicaments, des ordonnances et des réservations
}
