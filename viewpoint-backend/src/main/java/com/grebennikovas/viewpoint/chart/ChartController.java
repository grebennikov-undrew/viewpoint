package com.grebennikovas.viewpoint.chart;

import com.grebennikovas.viewpoint.chart.dto.ChartResponseDto;
import com.grebennikovas.viewpoint.chart.dto.ChartRequestDto;
import com.grebennikovas.viewpoint.chart.dto.ChartShortDto;
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

    /**
     * Получить список всех диаграмм
     * @return массив диаграмм
     * */
    @GetMapping("/")
    public ResponseEntity<List<ChartShortDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(chartService.findAll());
    }

    /**
     * Сохранить/изменить диаграмму
     * @param chartRequestDto настройки дмаграммы
     * @param userDetails пользователь, изменивший диаграмму
     * @return сохраненная диаграмма с данными
     * */
    @PostMapping("/")
    public ResponseEntity<ChartResponseDto> save(@RequestBody ChartRequestDto chartRequestDto,
                                  @AuthenticationPrincipal ViewPointUserDetails userDetails) throws SQLException {
        ChartResponseDto savedChart = chartService.save(chartRequestDto, userDetails.getId());
        return ResponseEntity.status(HttpStatus.OK).body(savedChart);
    }

    /**
     * Поулчить информацию о диаграмме по id
     * @param id id диаграммы
     * @return диаграмма с данными
     * */
    @GetMapping("/{id}")
    public ResponseEntity<ChartResponseDto> findById(@PathVariable Long id) throws SQLException {
        ChartResponseDto foundChart = chartService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundChart);
    }

    /**
     * Удалить диаграмму по id
     * @param id id диаграммы
     * @return сообщение об ошибке
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws SQLException {
        chartService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Получить данные из диагрммы по ее настройкам без сохранения (для редактора диаграмм)
    /**
     * Получить данные для диаграммы по её настройкам (для редактора)
     * @param chartRequestDto настройки диаграммы
     * @return диаграмма с данными
     * */
    @PostMapping("/data")
    public ResponseEntity<ChartResponseDto> getDataRaw(@RequestBody ChartRequestDto chartRequestDto) throws SQLException {
        ChartResponseDto newChart = chartService.getData(chartRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(newChart);
    }
}
