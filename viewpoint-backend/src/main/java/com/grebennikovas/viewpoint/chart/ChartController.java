package com.grebennikovas.viewpoint.chart;

import com.grebennikovas.viewpoint.chart.dto.ChartDataDto;
import com.grebennikovas.viewpoint.chart.dto.ChartDto;
import com.grebennikovas.viewpoint.datasets.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ChartDto> findAll() {
        return chartService.findAll();
    }

    // Сохранить/изменить диаграмму
    @PostMapping("/")
    public ChartDto save(@RequestBody ChartDto chartDto) {
        return chartService.save(chartDto);
    }

    // Поулчить информацию о диаграмме по id
    @GetMapping("/{id}")
    public ChartDto findById(@PathVariable Long id) throws SQLException {
        return chartService.findById(id);
    }

    // Получить данные из диагрммы по ее настройкам в БД (для дашборда)
    // TODO: возможно, этот метод не нужен
    @GetMapping("/{id}/data")
    public ChartDataDto getData(@PathVariable Long id) throws SQLException {
        return chartService.getData(id);
    }

    // Получить данные из диагрммы по ее настройкам без сохранения (для редактора диаграмм)
    @PostMapping("/data")
    public ChartDataDto getDataRaw(@RequestBody ChartDto chartDto) throws SQLException {
        return chartService.getData(chartDto);
    }

//    @GetMapping("/test")
//    public Chart test() {
//        ChartSettings settings = new ChartSettings();
//        settings.setWhere("123");
//        settings.setGroupBy(new ArrayList<>(List.of("col1","col2","col3")));
////        Map<String,Object> settings = new HashMap<>();
////        settings.put("where", "abc = 123");
////        settings.put("group by ", new ArrayList<>(List.of("col1","col2","col3")));
//        Chart c = new Chart();
//        c.setId(1L);
//        c.setName("test chart");
//        c.setChartSettings(settings);
//        return chartService.save(c);
//    }
}
