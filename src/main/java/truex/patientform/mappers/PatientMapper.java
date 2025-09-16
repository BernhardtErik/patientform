package truex.patientform.mappers;

import truex.patientform.dtos.PatientRequestDto;
import truex.patientform.dtos.PatientResponseDto;
import truex.patientform.entities.Patient;

public final class PatientMapper {
    private PatientMapper() {}

    public static Patient toEntity(PatientRequestDto dto) {
        if (dto == null) return null;
        Patient p = new Patient();
        p.setFirstName(dto.getFirstName());
        p.setLastName(dto.getLastName());
        p.setEmail(dto.getEmail());
        p.setPhoneNumber(dto.getPhoneNumber());
        p.setDateOfBirth(dto.getDateOfBirth());
        p.setIdNumber(dto.getIdNumber());
        p.setAddress(dto.getAddress());
        p.setMedicalAid(dto.getMedicalAid());
        p.setMedicalHistory(dto.getMedicalHistory());
        p.setAllergies(dto.getAllergies());
        p.setCurrentMedication(dto.getCurrentMedication());
        return p;
    }

    public static PatientResponseDto toDto(Patient entity) {
        if (entity == null) return null;
        PatientResponseDto dto = new PatientResponseDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setIdNumber(entity.getIdNumber());
        dto.setAddress(entity.getAddress());
        dto.setMedicalAid(entity.getMedicalAid());
        dto.setMedicalHistory(entity.getMedicalHistory());
        dto.setAllergies(entity.getAllergies());
        dto.setCurrentMedication(entity.getCurrentMedication());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
