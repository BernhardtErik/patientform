package truex.patientform.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "patient_information")
@Getter
@Setter
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    // Date of birth provided by user; keeping as LocalDate. If you meant auto-populate current date, set default below.
    private LocalDate dateOfBirth;

    // National ID / Passport number
    private String idNumber;

    private String address;
    private String medicalAid;

    @Column(length = 4000)
    private String medicalHistory;

    @Column(length = 1000)
    private String allergies;

    @Column(length = 2000)
    private String currentMedication;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
        // Auto-populate date of birth to today if not provided
        if (this.dateOfBirth == null) this.dateOfBirth = LocalDate.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }
}
