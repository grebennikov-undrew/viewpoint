package com.grebennikovas.viewpoint.security.rbac;

import com.grebennikovas.viewpoint.security.rbac.dto.RoleDto;
import com.grebennikovas.viewpoint.security.rbac.dto.RoleShortDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/role")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="Модуль управления ролями и привилегиями")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('READ ROLE LIST')")
    @Operation(summary = "Список ролей")
    public ResponseEntity<List<RoleShortDto>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ ROLE')")
    @Operation(summary = "Спписок привилегий и пользователей роли")
    public ResponseEntity<RoleDto> findById(@PathVariable Long id) throws SQLException {
        RoleDto foundRole = roleService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(foundRole);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('EDIT ROLE')")
    @Operation(summary = "Сохранить/изменить роль")
    public ResponseEntity<RoleDto> save(@RequestBody RoleDto roleDto) throws SQLException {
        RoleDto savedRole = roleService.save(roleDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(savedRole);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE ROLE')")
    @Operation(summary = "Удалить роль")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws SQLException {
        roleService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
