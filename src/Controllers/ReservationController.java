package Controllers;

import java.sql.*;
import java.util.Scanner;

public class ReservationController {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("1. Create New Reservation");
            System.out.println("2. Display Reservations");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    createNewReservation(scanner);
                    break;
                case 2:
                    displayReservations();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        }

        scanner.close();
        System.out.println("Program exited.");
    }
    public static void createNewReservation(Scanner scanner) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_shema2", "root", "");

            // Afficher les détails des patients
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

            // Demander à l'utilisateur d'entrer l'ID du patient et la date de réservation
            System.out.print("Enter patient ID: ");
            int patientId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter reservation date (YYYY-MM-DD): ");
            String reservationDate = scanner.nextLine();

            // Insérer la nouvelle réservation dans la base de données
            String sql = "INSERT INTO reservation (id_patient, date) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, patientId);
            preparedStatement.setString(2, reservationDate);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Reservation created successfully!");
            } else {
                System.out.println("Failed to create reservation.");
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    public static void displayReservations() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_shema2", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM reservation");

            System.out.println("Reservations list:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id_res");
                int patientId = resultSet.getInt("id_patient");
                String reservationDate = resultSet.getString("date");

                System.out.println("ID: " + id + ", Patient ID: " + patientId + ", Date: " + reservationDate);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error displaying reservations: " + e.getMessage());
        }
    }
}
