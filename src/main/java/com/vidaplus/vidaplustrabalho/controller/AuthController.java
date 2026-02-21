package com.vidaplus.vidaplustrabalho.controller;

import com.vidaplus.vidaplustrabalho.dto.LoginRequest;
import com.vidaplus.vidaplustrabalho.dto.SignUpRequest;
import com.vidaplus.vidaplustrabalho.dto.TokenResponse;
import com.vidaplus.vidaplustrabalho.model.Role;
import com.vidaplus.vidaplustrabalho.model.UserAccount;
import com.vidaplus.vidaplustrabalho.repository.UserAccountRepository;
import com.vidaplus.vidaplustrabalho.security.JwtService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
public class AuthController {

    private final UserAccountRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
        UserAccountRepository userRepo,
        PasswordEncoder passwordEncoder,
        JwtService jwtService
    ) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest req) {
        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já cadastrado");
        }

        Role perfil = req.getPerfil();
        if (perfil == null) {
            perfil = Role.PROFISSIONAL;
        }
        
        UserAccount user = new UserAccount(req.getEmail(), passwordEncoder.encode(req.getSenha()), perfil);
        userRepo.save(user);
        
        return ResponseEntity.ok(Map.of(
            "id", user.getId(),
            "email", user.getEmail(),
            "perfil", user.getPerfil().name()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        var optUser = userRepo.findByEmail(req.getEmail());
        if (optUser.isPresent()) {
            var u = optUser.get();
            if (
                passwordEncoder.matches(
                    req.getSenha(),
                    u.getSenhaCriptografada()
                )
            ) {
                return ResponseEntity.ok(
                    new TokenResponse(
                        jwtService.generateToken(
                            u.getEmail(),
                            u.getPerfil().name()
                        )
                    )
                );
            }
        }
        return ResponseEntity.status(401).body(
            Map.of("erro", "Credenciais inválidas")
        );
    }
}
