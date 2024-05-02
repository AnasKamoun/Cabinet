package Controllers;

import Models.Ordenance;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class OrdenanceController {
    private ArrayList<Ordenance> ordenances;

    public OrdenanceController() {
        ordenances = new ArrayList<>();
    }

    // Create a new ordenance
    public static void createNewOrdenance(OrdenanceController ordenanceController, Scanner scanner) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_shema2", "root", "");
            System.out.println("Patients:");
            Statement patientStatement = connection.createStatement();
            ResultSet patientResultSet = patientStatement.executeQuery("SELECT idPatient, nom FROM patient");
            while (patientResultSet.next()) {
                int patientId = patientResultSet.getInt("idPatient");
                String patientName = patientResultSet.getString("nom");
                System.out.println("ID: " + patientId + ", Name: " + patientName);
            }
            patientResultSet.close();
            patientStatement.close();

            // Afficher les détails des médicaments
            System.out.println("Medications:");
            Statement medicationStatement = connection.createStatement();
            ResultSet medicationResultSet = medicationStatement.executeQuery("SELECT id_med, name FROM medication");
            while (medicationResultSet.next()) {
                int medicationId = medicationResultSet.getInt("id_med");
                String medicationName = medicationResultSet.getString("name");
                System.out.println("ID: " + medicationId + ", Name: " + medicationName);
            }
            medicationResultSet.close();
            medicationStatement.close();
            System.out.println("Enter the ordenance details:");
            System.out.print("Patient ID: ");
            int patientId = scanner.nextInt();
            System.out.print("Medication ID: ");
            int medicationId = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Date (YYYY-MM-DD): ");
            String date = scanner.nextLine();

            Ordenance newOrdenance = new Ordenance(0, patientId, medicationId, date);
            ordenanceController.addOrdenance(newOrdenance);
            System.out.println("New ordenance added successfully!");


            String sql = "INSERT INTO ordenance (id_pat, id_me, date) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, patientId);
            preparedStatement.setInt(2, medicationId);
            preparedStatement.setString(3, date);
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int newId = rs.getInt(1);
                newOrdenance.setIdOrd(newId);  // Update the ordenance's ID with the generated key
            }
            System.out.println("New ordenance recorded in the database!");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // Add an ordenance to the controller's list
    public void addOrdenance(Ordenance ordenance) {
        ordenances.add(ordenance);
    }

    // Display all ordenances
    // Display all ordenances with patient names
    // Display all ordenances with patient names and medication names
    public static void displayAllOrdenances(OrdenanceController ordenanceController) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_shema2", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ordenance.id_ord, patient.nom AS patient_name, medication.name AS medication_name, ordenance.date FROM ordenance INNER JOIN patient ON ordenance.id_pat = patient.idPatient INNER JOIN medication ON ordenance.id_me = medication.id_med");

            System.out.println("Ordenances list:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id_ord");
                String patientName = resultSet.getString("patient_name"); // Get patient name
                String medicationName = resultSet.getString("medication_name"); // Get medication name
                String date = resultSet.getString("date");

                System.out.println("ID: " + id + ", Patient Name: " + patientName + ", Medication Name: " + medicationName + ", Date: " + date);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error displaying ordenances: " + e.getMessage());
        }
    }



    // Update an existing ordenance
    public static void updateOrdenance(OrdenanceController ordenanceController, Scanner scanner) {
        try {
            System.out.print("Enter the ID of the ordenance to update: ");
            int ordId = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_shema2", "root", "");
            String checkIdQuery = "SELECT COUNT(*) FROM ordenance WHERE id_ord = ?";
            PreparedStatement checkIdStatement = connection.prepareStatement(checkIdQuery);
            checkIdStatement.setInt(1, ordId);
            ResultSet resultSet = checkIdStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                System.out.println("1. Patient ID");
                System.out.println("2. Medication ID");
                System.out.println("3. Date");
                System.out.print("Select the field to update: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter the new patient ID: ");
                        int newPatientId = scanner.nextInt();
                        updateField(connection, "id_pat", newPatientId, ordId);
                        break;
                    case 2:
                        System.out.print("Enter the new medication ID: ");
                        int newMedicationId = scanner.nextInt();
                        updateField(connection, "id_me", newMedicationId, ordId);
                        break;
                    case 3:
                        System.out.print("Enter new date (YYYY-MM-DD): ");
                        String newDate = scanner.nextLine();
                        updateField(connection, "date", newDate, ordId);
                        break;
                    default:
                        System.out.println("Invalid option. Operation canceled.");
                        return;
                }
                System.out.println("Ordenance updated successfully!");
            } else {
                System.out.println("No ordenance found with this ID.");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error updating ordenance: " + e.getMessage());
        }
    }

    private static void updateField(Connection connection, String field, Object newValue, int id) throws SQLException {
        String updateQuery = "UPDATE ordenance SET " + field + " = ? WHERE id_ord = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
        updateStatement.setObject(1, newValue);
        updateStatement.setInt(2, id);
        updateStatement.executeUpdate();
    }

    // Delete an existing ordenance
    public static void deleteOrdenance(OrdenanceController ordenanceController, Scanner scanner) {
        try {
            System.out.print("Enter the ID of the ordenance to delete: ");
            int ordId = scanner.nextInt();

            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_shema2", "root", "");
            String checkIdQuery = "SELECT COUNT(*) FROM ordenance WHERE id_ord = ?";
            PreparedStatement checkIdStatement = connection.prepareStatement(checkIdQuery);
            checkIdStatement.setInt(1, ordId);
            ResultSet resultSet = checkIdStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                String deleteQuery = "DELETE FROM ordenance WHERE id_ord = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                deleteStatement.setInt(1, ordId);
                deleteStatement.executeUpdate();
                System.out.println("Ordenance deleted successfully!");
            } else {
                System.out.println("No ordenance found with this ID.");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error deleting ordenance: " + e.getMessage());
        }
    }
    public static void mainMenu() {
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
}
