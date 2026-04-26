package co.edu.ufps.legal_cases.security.controller;

import org.springframework.web.bind.annotation.*;

import co.edu.ufps.legal_cases.security.dto.LoginRequestDTO;
import co.edu.ufps.legal_cases.security.dto.LoginResponseDTO;
import co.edu.ufps.legal_cases.security.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO dto) {
        return authService.login(dto);
    }
}