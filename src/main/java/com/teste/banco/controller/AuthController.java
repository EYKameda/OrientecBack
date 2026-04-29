package com.teste.banco.controller;

import com.teste.banco.dto.LoginRequest;
import com.teste.banco.dto.LoginResponse;
import com.teste.banco.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * POST /api/auth/login
     * Realiza login do funcionário
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        try {
            LoginResponse response = authService.login(request, httpRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Login ou senha incorretos"));
        }
    }

    /**
     * GET /api/auth/validate
     * Valida token (opcional, para renovação)
     */
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String bearerToken) {
        try {
            String token = bearerToken.replace("Bearer ", "");
            // Adicione validação aqui
            return ResponseEntity.ok(new SuccessResponse("Token válido"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Token inválido"));
        }
    }
}

class ErrorResponse {
    public String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}

class SuccessResponse {
    public String message;

    public SuccessResponse(String message) {
        this.message = message;
    }
}
