package truex.patientform.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import truex.patientform.dtos.PatientRequestDto;
import truex.patientform.dtos.PatientResponseDto;
import truex.patientform.entities.Patient;
import truex.patientform.mappers.PatientMapper;
import truex.patientform.services.PatientService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/patients", produces = "application/json")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping
    public List<PatientResponseDto> list() {
        return service.getAll().stream().map(PatientMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<PatientResponseDto> search(@RequestParam(value = "query", required = false) String query) {
        return service.search(query).stream().map(PatientMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<PatientResponseDto> getOne(@PathVariable Long id) {
        return service.getById(id)
                .map(PatientMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ping")
    public String ping() { return "ok"; }

    @PostMapping
    public ResponseEntity<PatientResponseDto> create(@RequestBody PatientRequestDto request) {
        Patient toSave = PatientMapper.toEntity(request);
        Patient saved = service.create(toSave);
        PatientResponseDto body = PatientMapper.toDto(saved);
        return ResponseEntity.created(URI.create("/patients/" + saved.getId())).body(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> update(@PathVariable Long id, @RequestBody PatientRequestDto request) {
        Patient updates = PatientMapper.toEntity(request);
        return service.update(id, updates)
                .map(PatientMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean removed = service.delete(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
