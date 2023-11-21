package com.grebennikovas.viewpoint.dashboard;

import com.grebennikovas.viewpoint.dashboard.dto.*;
import com.grebennikovas.viewpoint.datasets.column.ColumnDto;
import com.grebennikovas.viewpoint.security.ViewPointUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="Модуль построения дашбордов")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    /**
     * Получить список дашбордов
     * @return список дашбордов в формте коротких DTO
     * */
    @GetMapping("/")
    @PreAuthorize("hasAuthority('READ DASHBOARD LIST')")
    @Operation(summary = "Все дашборды")
    public ResponseEntity<List<DashboardShortDto>> findAll() {
        List<DashboardShortDto> all = dashboardService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }

    /**
     * Сохранить новый/измененный дашборд
     * @param dashboardRequestDto новый дашборд
     * @param userDetails пользователь, внесший изменения
     * @return данные для построения дашборда
     * */
    @PostMapping("/")
    @PreAuthorize("hasAuthority('EDIT DASHBOARD')")
    @Operation(summary = "Сохранить/изменить дашборд")
    public ResponseEntity<DashboardResponseDto> save(@RequestBody DashboardRequestDto dashboardRequestDto,
                                  @AuthenticationPrincipal ViewPointUserDetails userDetails) throws SQLException {
        DashboardResponseDto savedDasboard = dashboardService.save(dashboardRequestDto, userDetails.getId());
        return ResponseEntity.status(HttpStatus.OK).body(savedDasboard);
    }

    /**
     * Поулчить данные для наполнения дашборда по id
     * @param id ID дашборда
     * @return данные для построения дашборда
     * */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ DASHBOARD')")
    @Operation(summary = "Данные для построения дашборда", description = "Настройки дашборда, настройки диаграмм и данные для их наполнения")
    public ResponseEntity<DashboardResponseDto> findById(@PathVariable Long id) throws SQLException {
        DashboardResponseDto foundDashboard = dashboardService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundDashboard);
    }

    // Поулчить отфильтрованные данные для наполнения дашборда по id
    /**
     * Поулчить данные для наполнения дашборда по id с фильтрами
     * @param id ID дашборда
     * @param columnFilters список значений для фильтров
     * @return данные для построения дашборда
     * */
    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('READ DASHBOARD')")
    @Operation(summary = "Данные для построения дашборда с фильтрами", description = "Настройки дашборда, настройки диаграмм и данные для их наполнения")
    public ResponseEntity<DashboardResponseDto> findByIdWithFilters(@PathVariable Long id, @RequestBody List<ColumnDto> columnFilters) throws SQLException {
        DashboardResponseDto foundDashboard = dashboardService.findById(id, columnFilters);
        return ResponseEntity.status(HttpStatus.OK).body(foundDashboard);
    }

    /**
     * Удалить дашборд по id
     * @param id id диаграммы
     * @return сообщение об ошибке
     * */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE DASHBOARD')")
    @Operation(summary = "Удалить дашборд")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws SQLException {
        dashboardService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
