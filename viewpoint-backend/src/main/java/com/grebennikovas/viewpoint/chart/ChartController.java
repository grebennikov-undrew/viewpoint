package com.grebennikovas.viewpoint.chart;

import com.grebennikovas.viewpoint.chart.dto.ChartResponseDto;
import com.grebennikovas.viewpoint.chart.dto.ChartRequestDto;
import com.grebennikovas.viewpoint.chart.dto.ChartShortDto;
import com.grebennikovas.viewpoint.dashboard.dto.DashboardResponseDto;
import com.grebennikovas.viewpoint.security.ViewPointUserDetails;
import com.grebennikovas.viewpoint.utils.CsvFormatter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chart")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="Модуль построения диаграмм")
public class ChartController {

    @Autowired
    ChartService chartService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('READ CHART LIST')")
    @Operation(summary = "Все диаграммы")
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
    @PreAuthorize("hasAuthority('EDIT CHART')")
    @Operation(summary = "Сохранить/изменить диаграмму")
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
    @PreAuthorize("hasAuthority('READ CHART')")
    @Operation(summary = "Данные для построения сохранённой диаграммы")
    public ResponseEntity<ChartResponseDto> findById(@PathVariable Long id) throws SQLException {
        ChartResponseDto foundChart = chartService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundChart);
    }

    @GetMapping("/{id}/export")
    @PreAuthorize("hasAuthority('READ CHART')")
    @Operation(summary = "Выгрузка данных диаграммы в формате CSV")
    public ResponseEntity<Resource> exportById(@PathVariable Long id) throws SQLException, IOException {
        ChartResponseDto foundChart = chartService.findById(id);

        List<String> columns = foundChart.getColumns();
        Map<String, Map<String, Object>> data = foundChart.getData();
        String fileName = URLEncoder.encode(foundChart.getName(), StandardCharsets.UTF_8) + ".csv";

        byte[] csvData = CsvFormatter.export(columns, data);
        ByteArrayResource result = new ByteArrayResource(csvData);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(csvData.length)
                .body(result);
    }

    /**
     * Удалить диаграмму по id
     * @param id id диаграммы
     * @return сообщение об ошибке
     * */
    @Operation(summary = "Удалить диграмму")
    @PreAuthorize("hasAuthority('DELETE CHART')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws SQLException {
        chartService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Получить данные для диаграммы по её настройкам (для редактора)
     * @param chartRequestDto настройки диаграммы
     * @return диаграмма с данными
     * */
    @PostMapping("/data")
    @PreAuthorize("hasAuthority('READ CHART')")
    @Operation(summary = "Данные для построения не сохранённой диаграммы")
    public ResponseEntity<ChartResponseDto> getDataRaw(@RequestBody ChartRequestDto chartRequestDto) throws SQLException {
        ChartResponseDto newChart = chartService.getData(chartRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(newChart);
    }
}
