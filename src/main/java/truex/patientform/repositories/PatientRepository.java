package truex.patientform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import truex.patientform.entities.Patient;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE " +
            "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(p.email) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(p.phoneNumber) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(p.idNumber) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(p.address) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Patient> search(@Param("q") String query);
}
