package truex.patientform.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import truex.patientform.entities.Patient;
import truex.patientform.repositories.PatientRepository;
import truex.patientform.services.PatientService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    public PatientServiceImpl(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Patient> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Patient> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return repository.findAll();
        }
        return repository.search(query.trim());
    }

    @Override
    public Optional<Patient> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Patient create(Patient patient) {
        return repository.save(patient);
    }

    @Override
    public Optional<Patient> update(Long id, Patient updates) {
        return repository.findById(id).map(existing -> {
            // Update mutable fields only if provided (nulls mean ignore)
            if (updates.getFirstName() != null) existing.setFirstName(updates.getFirstName());
            if (updates.getLastName() != null) existing.setLastName(updates.getLastName());
            if (updates.getEmail() != null) existing.setEmail(updates.getEmail());
            if (updates.getPhoneNumber() != null) existing.setPhoneNumber(updates.getPhoneNumber());
            if (updates.getDateOfBirth() != null) existing.setDateOfBirth(updates.getDateOfBirth());
            if (updates.getIdNumber() != null) existing.setIdNumber(updates.getIdNumber());
            if (updates.getAddress() != null) existing.setAddress(updates.getAddress());
            if (updates.getMedicalAid() != null) existing.setMedicalAid(updates.getMedicalAid());
            if (updates.getMedicalHistory() != null) existing.setMedicalHistory(updates.getMedicalHistory());
            if (updates.getAllergies() != null) existing.setAllergies(updates.getAllergies());
            if (updates.getCurrentMedication() != null) existing.setCurrentMedication(updates.getCurrentMedication());
            return repository.save(existing);
        });
    }

    @Override
    public boolean delete(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
