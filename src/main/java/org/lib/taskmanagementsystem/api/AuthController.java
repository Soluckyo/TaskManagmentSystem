package org.lib.taskmanagementsystem.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.lib.taskmanagementsystem.dto.JwtRequestDTO;
import org.lib.taskmanagementsystem.dto.JwtResponseDTO;
import org.lib.taskmanagementsystem.dto.RefreshTokenDTO;
import org.lib.taskmanagementsystem.dto.RegistrationUserDTO;
import org.lib.taskmanagementsystem.entity.User;
import org.lib.taskmanagementsystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@Tag(name = "auth_controller")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @Operation(
            summary = "Войти в систему",
            description = "Аутентифицирует пользователя"
            )
    @PostMapping("/sign_in")
    public ResponseEntity<JwtResponseDTO> signIn(@RequestBody JwtRequestDTO jwtRequestDTO){
        try{
            JwtResponseDTO jwtResponseDTO = authService.signIn(jwtRequestDTO);
            return ResponseEntity.ok(jwtResponseDTO);
        }catch (AuthenticationException e){
            throw new RuntimeException("Authentication failed" + e.getMessage());
        }
    }

    @Operation(
            summary = "Обновить токен",
            description = "Принимает RefreshToken и обновляет AccessToken"
    )
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDTO> refresh(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        try{
            JwtResponseDTO jwtResponseDTO = authService.refreshToken(refreshTokenDTO);
            return ResponseEntity.ok(jwtResponseDTO);
        }catch (Exception e){
            throw new RuntimeException("failed" + e.getMessage());
        }
    }

    @PostMapping("/add_user")
    public ResponseEntity<String> addUser(@RequestBody RegistrationUserDTO registrationUserDTO) {
        User user = new User();
        user.setUsername(registrationUserDTO.getUsername());
        user.setEmail(registrationUserDTO.getEmail());
        user.setPassword(registrationUserDTO.getPassword());

        authService.addUser(user);
        return ResponseEntity.ok("User added successfully");
    }
}

