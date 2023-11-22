package com.grebennikovas.viewpoint.users;

import com.grebennikovas.viewpoint.users.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="Модуль пользовательской информации")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Получить список пользователей
     * @return список пользователей в формте коротких DTO
     * */
    @GetMapping("/")
    @PreAuthorize("hasAuthority('READ USER LIST')")
    @Operation(summary = "Все пользователи")
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> foundUsers = userService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(foundUsers);
    }

    /**
     * Сохранить/изменить пользователя
     * @param newUser пользователь
     * @return сохраненный пользователь
     * */
    @PostMapping("/")
    @PreAuthorize("hasAuthority('EDIT USER')")
    @Operation(summary = "Сохранить/изменить пользователя")
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserDto newUser) {
        UserDto savedUser = userService.save(newUser);
        return ResponseEntity.status(HttpStatus.OK).body(savedUser);
    }

    /**
     * Удалить пользователя по id
     * @param id id пользователя
     * */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE USER')")
    @Operation(summary = "Удалить пользователя")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws SQLException {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
