package Controllers;

import Models.Patient_model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PatientController {
    private ArrayList<Patient_model> patients;

    public PatientController() {
        this.patients = new ArrayList<>();
    }
    public static void createNewPatient(PatientController patientController, Scanner scanner) {
        System.out.println("Création d'un nouveau patient :");
        System.out.print("Entrez le nom du patient : ");
        String nom = scanner.next();
        System.out.print("Entrez le prénom du patient : ");
        String prenom = scanner.next();
        System.out.print("Entrez l'adresse du patient : ");
        String adresse = scanner.next();
        System.out.print("Entrez le numéro de téléphone du patient : ");
        String numeroTelephone = scanner.next();

        // Créer un nouvel objet PatientModel avec les données fournies
        Patient_model newPatient = new Patient_model(nom, prenom, null, adresse, numeroTelephone);
        // Ajouter le nouveau patient à la liste des patients
        patientController.addPatient(newPatient);
        System.out.println("Nouveau patient ajouté avec succès !");
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_shema2", "root", "");
            String sql = "INSERT INTO patient (nom, prenom, adresse, numeroTelephone) VALUES (?, ?, ?, ?)"; // Removed idPatient from SQL
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nom); // Adjusted the parameter indices accordingly
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, adresse); // Vous devez ajouter la date de naissance ici
            preparedStatement.setString(4, numeroTelephone);
            preparedStatement.executeUpdate();
            System.out.println("Nouveau patient tsajel fl bd !");



        }   catch (Exception e){
            System.out.println(e);

        }

    }


    public void addPatient(Patient_model patient) {
        patients.add(patient);
    }
    public static void displayAllPatients(PatientController patientController) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_shema2", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PATIENT");

            // Parcourir le ResultSet et afficher les données de chaque patient
            while (resultSet.next()) {
                int id = resultSet.getInt("idPatient");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String adresse = resultSet.getString("adresse");
                String numeroTelephone = resultSet.getString("numeroTelephone");

                System.out.println("ID: "+id+", Nom: " + nom + ", Prénom: " + prenom + ", Adresse: " + adresse + ", Numéro de téléphone: " + numeroTelephone);
            }

            // Fermeture des ressources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite lors de l'affichage des patients : " + e.getMessage());
        }
    }

    // Update
    public static void updatePatientInfo(PatientController patientController, Scanner scanner) {
        // Demander à l'utilisateur de saisir l'ID du patient à mettre à jour
        System.out.print("Entrez l'ID du patient à mettre à jour : ");
        int patientId = scanner.nextInt();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_shema2", "root", "")) {
            // Vérifier si le patient existe dans la base de données
            String checkIdQuery = "SELECT COUNT(*) FROM PATIENT WHERE idPatient = ?";
            PreparedStatement checkIdStatement = connection.prepareStatement(checkIdQuery);
            checkIdStatement.setInt(1, patientId);
            ResultSet resultSet = checkIdStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                // Demander à l'utilisateur quel champ mettre à jour
                System.out.println("Quel champ voulez-vous mettre à jour pour le patient avec l'ID " + patientId + " ?");
                System.out.println("1. Nom");
                System.out.println("2. Prénom");
                System.out.println("3. Adresse");
                System.out.println("4. Numéro de téléphone");
                System.out.print("Choisissez une option : ");
                int choice = scanner.nextInt();

                // Déclarer les variables pour stocker les nouvelles valeurs
                String newNom, newPrenom, newAdresse, newNumeroTelephone;

                // Mettre à jour le champ sélectionné
                switch (choice) {
                    case 1:
                        System.out.print("Entrez le nouveau nom du patient : ");
                        newNom = scanner.next();
                        // Mettre à jour le nom dans la base de données
                        String updateQuery = "UPDATE PATIENT SET nom = ? WHERE idPatient = ?";
                        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                        updateStatement.setString(1, newNom);
                        updateStatement.setInt(2, patientId);
                        updateStatement.executeUpdate();
                        break;
                    case 2:
                        System.out.print("Entrez le nouveau prénom du patient : ");
                        newPrenom = scanner.next();
                        // Mettre à jour le prénom dans la base de données
                        String updatePrenomQuery = "UPDATE PATIENT SET prenom = ? WHERE idPatient = ?";
                        PreparedStatement updatePrenomStatement = connection.prepareStatement(updatePrenomQuery);
                        updatePrenomStatement.setString(1, newPrenom);
                        updatePrenomStatement.setInt(2, patientId);
                        updatePrenomStatement.executeUpdate();
                        break;

                    case 3:
                        System.out.print("Entrez la nouvelle adresse du patient : ");
                        newAdresse = scanner.next(); // Ou scanner.nextLine() si vous voulez permettre des espaces
                        // Mettre à jour l'adresse dans la base de données
                        String updateAdresseQuery = "UPDATE PATIENT SET adresse = ? WHERE idPatient = ?";
                        PreparedStatement updateAdresseStatement = connection.prepareStatement(updateAdresseQuery);
                        updateAdresseStatement.setString(1, newAdresse);
                        updateAdresseStatement.setInt(2, patientId);
                        updateAdresseStatement.executeUpdate();
                        break;

                    case 4:
                        System.out.print("Entrez le nouveau numéro de téléphone du patient : ");
                        newNumeroTelephone = scanner.next();
                        // Mettre à jour le numéro de téléphone dans la base de données
                        String updateNumeroQuery = "UPDATE PATIENT SET numeroTelephone = ? WHERE idPatient = ?";
                        PreparedStatement updateNumeroStatement = connection.prepareStatement(updateNumeroQuery);
                        updateNumeroStatement.setString(1, newNumeroTelephone);
                        updateNumeroStatement.setInt(2, patientId);
                        updateNumeroStatement.executeUpdate();
                        break;

                    default:
                        System.out.println("Option invalide. Opération annulée.");
                        return;
                }

                System.out.println("Informations du patient mises à jour avec succès !");
            } else {
                System.out.println("Aucun patient trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour des informations du patient : " + e.getMessage());
        }
    }


    // Delete
    public void deletePatient(int index) {
        if (index >= 0 && index < patients.size()) {
            patients.remove(index);
            patients.remove(index);
        } else {
            // Gérer le cas où l'index est invalide
            // Par exemple, lever une exception ou afficher un message d'erreur
        }
    }

    public static void deletePatient(PatientController patientController, Scanner scanner) {
        // Demander à l'utilisateur de saisir l'ID du patient à supprimer
        System.out.print("Entrez l'ID du patient à supprimer : ");
        int patientId = scanner.nextInt();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_shema2", "root", "")) {
            // Vérifier si le patient existe dans la base de données
            String checkIdQuery = "SELECT COUNT(*) FROM PATIENT WHERE idPatient = ?";
            PreparedStatement checkIdStatement = connection.prepareStatement(checkIdQuery);
            checkIdStatement.setInt(1, patientId);
            ResultSet resultSet = checkIdStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                // Supprimer le patient de la base de données
                String deleteQuery = "DELETE FROM PATIENT WHERE idPatient = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                deleteStatement.setInt(1, patientId);
                deleteStatement.executeUpdate();
                System.out.println("Patient supprimé avec succès !");
            } else {
                System.out.println("Aucun patient trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du patient : " + e.getMessage());
        }
    }

    // Pour obtenir tous les patients
    public ArrayList<Patient_model> getAllPatients() {
        return patients;
    }
}
