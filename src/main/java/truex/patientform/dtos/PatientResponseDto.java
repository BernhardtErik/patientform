package truex.patientform.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
public class PatientResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String idNumber;
    private String address;
    private String medicalAid;
    private String medicalHistory;
    private String allergies;
    private String currentMedication;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
