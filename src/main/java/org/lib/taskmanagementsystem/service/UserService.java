package org.lib.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.lib.taskmanagementsystem.dto.JwtRequestDTO;
import org.lib.taskmanagementsystem.dto.JwtResponseDTO;
import org.lib.taskmanagementsystem.dto.RefreshTokenDTO;
import org.lib.taskmanagementsystem.entity.Role;
import org.lib.taskmanagementsystem.entity.Status;
import org.lib.taskmanagementsystem.entity.Task;
import org.lib.taskmanagementsystem.entity.User;
import org.lib.taskmanagementsystem.jwt.JwtUtils;
import org.lib.taskmanagementsystem.repository.TaskRepo;
import org.lib.taskmanagementsystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{

    private final TaskRepo taskRepo;

    public void changeStatus(Long taskId, User user, Status status) throws AccessDeniedException {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Задача с ID: " + taskId + " не найдена"));
        if (!task.getUser().equals(user)) {
            throw new AccessDeniedException(" Вы не можете редактировать эту задачу");
        }
        task.setStatus(status);
        taskRepo.save(task);
    }

    public void addComment(Long taskId,User user, String comment) throws AccessDeniedException {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Задача с ID: " + taskId + " не найдена"));
        if (!task.getUser().equals(user)) {
            throw new AccessDeniedException(" Вы не можете редактировать эту задачу");
        }
        task.setComment(comment);
        taskRepo.save(task);
    }
}
