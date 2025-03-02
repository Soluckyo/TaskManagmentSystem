package org.lib.taskmanagementsystem.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.lib.taskmanagementsystem.dto.RegistrationUserDTO;
import org.lib.taskmanagementsystem.entity.User;
import org.lib.taskmanagementsystem.jwt.JwtRequest;
import org.lib.taskmanagementsystem.jwt.JwtResponce;
import org.lib.taskmanagementsystem.jwt.RefreshJwtRequest;
import org.lib.taskmanagementsystem.service.AuthService;
import org.lib.taskmanagementsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth_controller")
@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Operation(
            summary = "Залогиниться",
            description = "Ищет пользователя по email. Если пользователь найден и присланный пароль совпадает" +
                    " с сохранённым паролем, объект пользователя передается в JwtProvider для генерации токенов"
    )
    @PostMapping("/login")
    public ResponseEntity<JwtResponce> login(@RequestBody JwtRequest authRequest) {
        final JwtResponce token;
        try {
            token = authService.login(authRequest);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Получить новый AccessToken",
            description = "Получает RefreshToken, проверяет его валидность и выдает свежий AccessToken"
    )
    @PostMapping("/token")
    public ResponseEntity<JwtResponce> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponce token;
        try{
            token = authService.getAccessToken(request.getRefreshToken());
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Выдать новый RefreshToken",
            description = "Получает RefreshToken, проверяет его валидность и выдает новый RefreshToken и AccessToken"
    )
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponce> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponce token;
        try{
            token = authService.refreshToken(request.getRefreshToken());
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @Operation(
            summary = "Создание нового пользователя",
            description = "Принимает сущность RegistrationUserDTO, проверяет не существует ли уже такой пользователь, после создает"
    )
    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDTO registrationUserDTO){
        if(userService.findByEmail(registrationUserDTO.getUsername()) != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь c таким email уже существует");
        }
        User user = new User();
        user.setUsername(registrationUserDTO.getUsername());
        user.setPassword(registrationUserDTO.getPassword());
        user.setEmail(registrationUserDTO.getEmail());
        authService.createNewUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
