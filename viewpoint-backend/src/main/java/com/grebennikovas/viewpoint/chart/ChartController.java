package com.grebennikovas.viewpoint.chart;

import com.grebennikovas.viewpoint.chart.service.ChartFactory;
import com.grebennikovas.viewpoint.chart.service.ChartService;
import com.grebennikovas.viewpoint.chart.service.TableChartService;
import com.grebennikovas.viewpoint.datasets.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chart")
@CrossOrigin(origins = "http://localhost:3000")
public class ChartController {

    @Autowired
    ChartRepository chartRepository;

    @Autowired
    private ChartFactory chartFactory;

    // Получить все диаграммы
    @GetMapping("/")
    public List<Chart> findAll() {
        ChartService chartService = chartFactory.getChartService(ChartType.TABLE);
        return chartService.findAll();
    }

    // Сохранить/изменить диаграмму
    @PostMapping("/")
    public Chart save(@RequestBody Chart chart) {
        ChartService chartService = chartFactory.getChartService(chart.getChartType());
        return chartService.save(chart);
    }

    @GetMapping("/{id}/data")
    public Result getData(@PathVariable Long id) {
        Chart chart = chartRepository.findById(id).get();
        ChartService chartService = chartFactory.getChartService(chart.getChartType());
        return chartService.getData(chart);
    }

    @GetMapping("/test")
    public Chart test() {
        ChartService chartService = chartFactory.getChartService(ChartType.TABLE);
        ChartSettings settings = new ChartSettings();
        settings.setWhere("123");
        settings.setGroupBy(new ArrayList<>(List.of("col1","col2","col3")));
//        Map<String,Object> settings = new HashMap<>();
//        settings.put("where", "abc = 123");
//        settings.put("group by ", new ArrayList<>(List.of("col1","col2","col3")));
        Chart c = new Chart();
        c.setId(1L);
        c.setName("test chart");
        c.setChartSettings(settings);
        return chartService.save(c);
    }
}
