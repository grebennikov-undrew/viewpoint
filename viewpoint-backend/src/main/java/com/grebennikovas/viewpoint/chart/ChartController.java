package com.grebennikovas.viewpoint.chart;

import com.grebennikovas.viewpoint.chart.dto.ChartResponseDto;
import com.grebennikovas.viewpoint.chart.dto.ChartRequestDto;
import com.grebennikovas.viewpoint.dashboard.dto.DashboardResponseDto;
import com.grebennikovas.viewpoint.security.ViewPointUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/chart")
@CrossOrigin(origins = "http://localhost:3000")
public class ChartController {

    @Autowired
    ChartService chartService;

    // Получить все диаграммы
    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(chartService.findAll());
    }

    // Сохранить/изменить диаграмму
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody ChartRequestDto chartRequestDto,
                                  @AuthenticationPrincipal ViewPointUserDetails userDetails) {
        try {
            ChartResponseDto savedChart = chartService.save(chartRequestDto, userDetails.getId());
            return ResponseEntity.status(HttpStatus.OK).body(savedChart);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Поулчить информацию о диаграмме по id
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            ChartResponseDto foundChart = chartService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(foundChart);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Получить данные из диагрммы по ее настройкам без сохранения (для редактора диаграмм)
    @PostMapping("/data")
    public ResponseEntity<?> getDataRaw(@RequestBody ChartRequestDto chartRequestDto) {
        try {
            ChartResponseDto newChart = chartService.getData(chartRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(newChart);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
