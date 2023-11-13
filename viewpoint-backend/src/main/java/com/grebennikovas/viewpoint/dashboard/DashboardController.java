package com.grebennikovas.viewpoint.dashboard;

import com.grebennikovas.viewpoint.chart.dto.ChartDataDto;
import com.grebennikovas.viewpoint.chart.dto.ChartDto;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {
    @Autowired
    DashboardService dashboardService;

//    @GetMapping("/parameter/{id}")
//    public ResponseEntity<?> getParameterValues(@PathVariable Long id) {
//        try {
//            List<String> paramValues = dashboardService.getFilterValues(id);
//            return ResponseEntity.status(HttpStatus.OK).body(paramValues);
//        } catch (SQLException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

    // Получить все диаграммы
    @GetMapping("/")
    public List<Dashboard> findAll() {
        List<Dashboard> all = dashboardService.findAll();
        return all;
    }

    // Сохранить/изменить диаграмму
    @PostMapping("/")
    public Dashboard save(@RequestBody Dashboard dashboard) {
        return dashboardService.save(dashboard);
    }

    // Поулчить информацию о диаграмме по id
    @GetMapping("/{id}")
    public Dashboard findById(@PathVariable Long id) throws SQLException {
        return dashboardService.findById(id);
    }

    @GetMapping("/{id}/data")
    public List<ChartDataDto> getData(@PathVariable Long id) throws SQLException {
        return dashboardService.getData(id);
    }

    @PostMapping("/parameter")
    public ResponseEntity<?> getParameterValues(@RequestBody ParameterDto parameterDTO) {
        Long sourceId = parameterDTO.getSourceId();
        String sqlQuery = parameterDTO.getSqlQuery();
        try {
            List<String> paramValues = dashboardService.getFilterValues(sourceId, sqlQuery);
            return ResponseEntity.status(HttpStatus.OK).body(paramValues);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
