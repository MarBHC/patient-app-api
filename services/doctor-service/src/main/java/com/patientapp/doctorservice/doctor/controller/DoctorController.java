package com.patientapp.doctorservice.doctor.controller;

import com.patientapp.doctorservice.doctor.dto.DoctorRequestDTO;
import com.patientapp.doctorservice.doctor.entity.Doctor;
import com.patientapp.doctorservice.doctor.service.interfaces.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @Operation(summary = "Crear un nuevo doctor")
    @PostMapping
    public ResponseEntity<?> createDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {
        try {
            Doctor doctor = doctorService.createDoctor(doctorRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Obtener todos los doctores activos")
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllActiveDoctors() {
        List<Doctor> doctors = doctorService.getAllActiveDoctors();
        return ResponseEntity.ok(doctors);
    }

    @Operation(summary = "Obtener un doctor por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(
            @Parameter(description = "UUID del doctor") @PathVariable UUID id
    ) {
        try {
            Doctor doctor = doctorService.getDoctorById(id);
            return ResponseEntity.ok(doctor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor no encontrado");
        }
    }

    @Operation(summary = "Actualizar información de un doctor")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(
            @Parameter(description = "UUID del doctor") @PathVariable UUID id,
            @RequestBody DoctorRequestDTO doctorRequestDTO
    ) {
        try {
            Doctor updatedDoctor = doctorService.updateDoctor(id, doctorRequestDTO);
            return ResponseEntity.ok(updatedDoctor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor no encontrado");
        }
    }

    @Operation(summary = "Desactivar un doctor")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deactivateDoctor(
            @Parameter(description = "UUID del doctor") @PathVariable UUID id
    ) {
        try {
            doctorService.deactivateDoctor(id);
            return ResponseEntity.ok("Doctor desactivado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor no encontrado");
        }
    }
}
