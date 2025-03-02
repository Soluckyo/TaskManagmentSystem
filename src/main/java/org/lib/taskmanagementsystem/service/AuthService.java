package org.lib.taskmanagementsystem.service;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.lib.taskmanagementsystem.entity.Role;
import org.lib.taskmanagementsystem.entity.User;
import org.lib.taskmanagementsystem.jwt.JwtAuthentication;
import org.lib.taskmanagementsystem.jwt.JwtProvider;
import org.lib.taskmanagementsystem.jwt.JwtRequest;
import org.lib.taskmanagementsystem.jwt.JwtResponce;
import org.lib.taskmanagementsystem.repository.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final UserRepo userRepo;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public JwtResponce login(@NonNull JwtRequest authRequest) throws AuthException {
        final User user = userService.getByEmail(authRequest.getEmail())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        if(user.getPassword().equals(authRequest.getPassword())){
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getEmail(), refreshToken);
            return new JwtResponce(accessToken, refreshToken);
        }else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponce getAccessToken(@NonNull String refreshToken) throws AuthException {
        if(jwtProvider.validateRefreshToken(refreshToken)){
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if(saveRefreshToken != null && saveRefreshToken.equals(refreshToken)){
                final User user = userService.getByEmail(email)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponce(accessToken, null);
            }
        }
        return new JwtResponce(null, null);
    }

    public JwtResponce refreshToken(@NonNull String refreshToken) throws AuthException {
        if(jwtProvider.validateRefreshToken(refreshToken)){
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if(saveRefreshToken != null && saveRefreshToken.equals(refreshToken)){
                final User user = userService.getByEmail(email)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(email, newRefreshToken);
                return new JwtResponce(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный токен");
    }

    public JwtAuthentication getAuthInfo(){
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    public void createNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

}
