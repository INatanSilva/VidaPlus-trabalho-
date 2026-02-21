package com.vidaplus.vidaplustrabalho.controller;

import com.vidaplus.vidaplustrabalho.model.Paciente;
import com.vidaplus.vidaplustrabalho.repository.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @PostMapping
    public Paciente create(@Valid @RequestBody Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @GetMapping("/{id}")
    public Paciente findById(@PathVariable Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));
    }

    @PutMapping("/{id}")
    public Paciente update(@PathVariable Long id, @Valid @RequestBody Paciente pacienteDetails) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));

        paciente.setNome(pacienteDetails.getNome());
        paciente.setCpf(pacienteDetails.getCpf());
        paciente.setEndereco(pacienteDetails.getEndereco());

        return pacienteRepository.save(paciente);
    }
}
