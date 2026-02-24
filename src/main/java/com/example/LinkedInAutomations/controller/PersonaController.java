package com.example.LinkedInAutomations.controller;

import com.example.LinkedInAutomations.dto.PersonaRequestDTO;
import com.example.LinkedInAutomations.entity.Persona;
import com.example.LinkedInAutomations.service.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persona")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;

    @PostMapping
    public Persona savePersona(
            Authentication authentication,
            @RequestBody PersonaRequestDTO dto) {

        String email = authentication.getName();
        return personaService.saveOrUpdatePersona(email, dto);
    }

    @GetMapping
    public Persona getPersona(Authentication authentication) {

        String email = authentication.getName();
        return personaService.getPersona(email);
    }
}