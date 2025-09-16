package truex.patientform.services;

import truex.patientform.entities.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    List<Patient> getAll();
    List<Patient> search(String query);
    Optional<Patient> getById(Long id);
    Patient create(Patient patient);
    Optional<Patient> update(Long id, Patient updates);
    boolean delete(Long id);
}