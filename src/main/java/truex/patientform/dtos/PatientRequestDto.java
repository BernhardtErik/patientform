package truex.patientform.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth; // optional; entity will default to today if null
    private String idNumber;
    private String address;
    private String medicalAid;
    private String medicalHistory;
    private String allergies;
    private String currentMedication;
}
