package Models;

import java.io.Serializable;

public class Medication implements Serializable {
    private int idMed;
    private String name;
    private String dosage;
    private String instructions;

    // Constructor
    public Medication(int idMed, String name, String dosage, String instructions) {
        this.idMed = idMed;
        this.name = name;
        this.dosage = dosage;
        this.instructions = instructions;
    }

    // Getters and setters
    public int getIdMed() {
        return idMed;
    }

    public void setIdMed(int idMed) {
        this.idMed = idMed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
