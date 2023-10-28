package com.grebennikovas.viewpoint.dashboard;

import com.grebennikovas.viewpoint.datasets.dto.DatasetDTO;
import com.grebennikovas.viewpoint.datasets.dto.ParameterDTO;
import com.grebennikovas.viewpoint.datasets.parameter.dto.ParameterTempDTO;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {
    @Autowired
    DashboardService dashboardService;

    @GetMapping("/parameter/{id}")
    public List<String> getParameterValues(@PathVariable Long id) {
        return dashboardService.getFilterValues(id);
    }

    @PostMapping("/parameter")
    public List<String> getParameterValues(@RequestBody ParameterTempDTO parameterTempDTO) {
        Long sourceId = parameterTempDTO.getSourceId();
        String sqlQuery = parameterTempDTO.getSqlQuery();
        return dashboardService.getFilterValues(sourceId, sqlQuery);
    }

}
