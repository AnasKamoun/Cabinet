package Models;

public class Patient {
    private String name;
    private int age;
    private String gender;
    private String medicalHistory;

    public Patient(String name, int age, String gender, String medicalHistory) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.medicalHistory = medicalHistory;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }
}
