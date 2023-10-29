package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.ViewpointApplication;
import org.junit.jupiter.api.Test;
import com.grebennikovas.viewpoint.datasets.column.Column;
import com.grebennikovas.viewpoint.datasets.column.ColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@ContextConfiguration(classes=ViewpointApplication.class)
@SpringBootTest
public class DatasetServiceIntegrationTest {
    @Autowired
    private ColumnRepository columnRepository;
    @Autowired
    private DatasetRepository datasetRepository;
    @Autowired
    private DatasetService datasetService;

    // Обновление колонок с только новым списком (актуально для новых датасетов)
    @Test
    public void testUpsertColumnsOnlyNew() {
        // Создание датасета
        Dataset dataset = new Dataset();
        dataset.setName("Test Dataset");
        datasetRepository.save(dataset);

        // Создание колонок для датасета
        List<Column> newColumns = new ArrayList<>();
        Column column1 = new Column();
        column1.setName("Column 1");
        column1.setType("Type 1");
        newColumns.add(column1);

        Column column2 = new Column();
        column2.setName("Column 2");
        column2.setType("Type 2");
        newColumns.add(column2);

        // Вызов метода
        List<Column> result = datasetService.upsertColumns(newColumns, dataset);

        // Проверка
        assertNotNull(result);
        // Проверка на количество
        assertEquals(2, result.size());
        // Проверка, что все значения сохранились в БД
        List<Column> savedColumns = columnRepository.findAll();
        assertEquals(2, savedColumns.size());
        // Проверки на значения
        assertEquals("Column 1", savedColumns.get(0).getName());
        assertEquals("Type 1", savedColumns.get(0).getType());
        assertEquals("Column 2", savedColumns.get(1).getName());
        assertEquals("Type 2", savedColumns.get(1).getType());
    }
}

