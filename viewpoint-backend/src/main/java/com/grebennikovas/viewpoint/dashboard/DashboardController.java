package com.grebennikovas.viewpoint.dashboard;

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
