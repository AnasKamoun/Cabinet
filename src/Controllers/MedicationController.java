package Controllers;

import Models.Medication;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MedicationController {
    private ArrayList<Medication> medications;

    public MedicationController() {
        medications = new ArrayList<>();
    }

    public static void createNewMedication(MedicationController medicationController, Scanner scanner) {
        System.out.println("Enter the medication details:");
        System.out.print("Name: ");
        String name = scanner.next();
        System.out.print("Dosage: ");
        String dosage = scanner.next();
        System.out.print("Instructions: ");
        scanner.nextLine();  // Clear the buffer
        String instructions = scanner.nextLine();

        Medication newMedication = new Medication(0, name, dosage, instructions);
        medicationController.addMedication(newMedication);
        System.out.println("New medication added successfully!");

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_shema", "root", "");
            String sql = "INSERT INTO medication (name, dosage, instructions) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, dosage);
            preparedStatement.setString(3, instructions);
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int newId = rs.getInt(1);
                newMedication.setIdMed(newId);  // Update the medication's ID with the generated key
            }
            System.out.println("New medication recorded in the database!");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void addMedication(Medication medication) {
        medications.add(medication);
    }

    public static void displayAllMedications(MedicationController medicationController) {
        System.out.println("Medications list:");
        for (Medication med : medicationController.getAllMedications()) {
            System.out.println("ID: " + med.getIdMed() + ", Name: " + med.getName() + ", Dosage: " + med.getDosage() + ", Instructions: " + med.getInstructions());
        }
    }

    public ArrayList<Medication> getAllMedications() {
        return medications;
    }

    public static void updateMedication(MedicationController medicationController, Scanner scanner) {
        System.out.print("Enter the ID of the medication to update: ");
        int medId = scanner.nextInt();
        scanner.nextLine();  // Clear buffer after reading integer

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_shema", "root", "")) {
            String checkIdQuery = "SELECT COUNT(*) FROM medication WHERE id_med = ?";
            PreparedStatement checkIdStatement = connection.prepareStatement(checkIdQuery);
            checkIdStatement.setInt(1, medId);
            ResultSet resultSet = checkIdStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                System.out.println("1. Name");
                System.out.println("2. Dosage");
                System.out.println("3. Instructions");
                System.out.print("Select the field to update: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Clear buffer after reading integer

                switch (choice) {
                    case 1:
                        System.out.print("Enter the new name: ");
                        String newName = scanner.nextLine();
                        updateField(connection, "name", newName, medId);
                        break;
                    case 2:
                        System.out.print("Enter the new dosage: ");
                        String newDosage = scanner.nextLine();
                        updateField(connection, "dosage", newDosage, medId);
                        break;
                    case 3:
                        System.out.print("Enter new instructions: ");
                        String newInstructions = scanner.nextLine();
                        updateField(connection, "instructions", newInstructions, medId);
                        break;
                    default:
                        System.out.println("Invalid option. Operation canceled.");
                        return;
                }
                System.out.println("Medication updated successfully!");
            } else {
                System.out.println("No medication found with this ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating medication: " + e.getMessage());
        }
    }

    private static void updateField(Connection connection, String field, String newValue, int id) throws SQLException {
        String updateQuery = "UPDATE medication SET " + field + " = ? WHERE id_med = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
        updateStatement.setString(1, newValue);
        updateStatement.setInt(2, id);
        updateStatement.executeUpdate();
    }

    public static void deleteMedication(MedicationController medicationController, Scanner scanner) {
        System.out.print("Enter the ID of the medication to delete: ");
        int medId = scanner.nextInt();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_shema", "root", "")) {
            String checkIdQuery = "SELECT COUNT(*) FROM medication WHERE id_med = ?";
            PreparedStatement checkIdStatement = connection.prepareStatement(checkIdQuery);
            checkIdStatement.setInt(1, medId);
            ResultSet resultSet = checkIdStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                String deleteQuery = "DELETE FROM medication WHERE id_med = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                deleteStatement.setInt(1, medId);
                deleteStatement.executeUpdate();
                System.out.println("Medication deleted successfully!");
            } else {
                System.out.println("No medication found with this ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting medication: " + e.getMessage());
        }
    }
}
