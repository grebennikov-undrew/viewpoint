package com.grebennikovas.viewpoint.dashboard;

import com.grebennikovas.viewpoint.dashboard.dto.DashboardRequestDto;
import com.grebennikovas.viewpoint.dashboard.dto.DashboardResponseDto;
import com.grebennikovas.viewpoint.dashboard.dto.DashboardShortDto;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterDto;
import com.grebennikovas.viewpoint.security.ViewPointUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<?> findAll() {
        List<DashboardShortDto> all = dashboardService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }

    // Сохранить/изменить диаграмму
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody DashboardRequestDto dashboardRequestDto,
                                  @AuthenticationPrincipal ViewPointUserDetails userDetails) {
        try {
            DashboardResponseDto savedDasboard = dashboardService.save(dashboardRequestDto, userDetails.getId());
            return ResponseEntity.status(HttpStatus.OK).body(savedDasboard);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Поулчить информацию о дашборде по id
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            DashboardResponseDto foundDashboard = dashboardService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(foundDashboard);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/data")
    public ResponseEntity<?> getData(@RequestBody DashboardRequestDto dashboardRequestDto) {
        try {
            DashboardResponseDto newDashboard = dashboardService.getData(dashboardRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(newDashboard);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    @GetMapping("/{id}/data")
//    public List<ChartDataDto> getData(@PathVariable Long id) throws SQLException {
//        return dashboardService.getData(id);
//    }

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
