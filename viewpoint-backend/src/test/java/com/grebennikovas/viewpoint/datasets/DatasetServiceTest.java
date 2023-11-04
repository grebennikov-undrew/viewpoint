package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.column.Column;
import com.grebennikovas.viewpoint.datasets.dto.DatasetDTO;
import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.users.User;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DatasetServiceTest {

    @Test
    void testMapDataset1() {
        // Зависимые сущности
        User user = new User();
        user.setId(1L);
        user.setUsername("test_user");
        Source source = new Source();
        source.setId(1L);
        source.setName("Test Source");

        // объект Dataset
        Dataset dataset = new Dataset();
        dataset.setId(1L);
        dataset.setName("Test Dataset");
        dataset.setSqlQuery("SELECT * FROM table");
        dataset.setUser(user);
        dataset.setSource(source);

        // Создание мок
        DatasetService datasetService = mock(DatasetService.class);
        when(datasetService.mapDataset(dataset)).thenReturn(new DatasetDTO(dataset));

        // Вызов метода
        DatasetDTO result = datasetService.mapDataset(dataset);

        // Проверки
        assertNotNull(result);
        assertEquals(dataset.getId(), result.getId());
        assertEquals(dataset.getName(), result.getName());
        assertEquals(dataset.getSqlQuery(), result.getSqlQuery());
        assertEquals(dataset.getUser().getId(), result.getUser().getId());
        assertEquals(dataset.getSource().getId(), result.getSource().getId());
        assertEquals(dataset.getSource().getName(), result.getSource().getName());
    }

    @Test
    public void testMapDataset2() {
        // Создание объектов
        Source source = new Source(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("test_user");

        Dataset dataset = new Dataset();
        dataset.setId(1L);
        dataset.setName("Test Dataset");
        dataset.setSqlQuery("SELECT * FROM table");
        dataset.setSource(source);
        dataset.setUser(user);

        List<Column> columns = new ArrayList<>();
        Column column1 = new Column();
        column1.setId(1L);
        column1.setName("Column 1");
        column1.setType("Some Type");
        column1.setDataset(dataset);
        columns.add(column1);

        List<Parameter> params = new ArrayList<>();
        Parameter param1 = new Parameter();
        param1.setId(1L);
        param1.setName("Parameter 1");
        param1.setType("Some Type");
        param1.setSqlQuery("SELECT field FROM table");
        param1.setDataset(dataset);
        params.add(param1);

        // Создание мок
        DatasetService datasetService = mock(DatasetService.class);
        when(datasetService.mapDataset(dataset, columns, params)).thenReturn(new DatasetDTO(dataset, columns, params));

        // Вызов метода
        DatasetDTO result = datasetService.mapDataset(dataset, columns, params);

        // Проверки
        assertNotNull(result);
        assertEquals(dataset.getId(), result.getId());
        assertEquals(dataset.getName(), result.getName());
        assertEquals(dataset.getSqlQuery(), result.getSqlQuery());
        assertEquals(column1.getName(), result.getColumns().get(0).getName());
        assertEquals(column1.getId(), result.getColumns().get(0).getId());
        assertEquals(column1.getType(), result.getColumns().get(0).getType());
        assertEquals(param1.getName(), result.getParameters().get(0).getName());
        assertEquals(param1.getId(), result.getParameters().get(0).getId());
        assertEquals(param1.getType(), result.getParameters().get(0).getType());
        assertEquals(param1.getSqlQuery(), result.getParameters().get(0).getSqlQuery());
        // Самая главная проверка, что у DTO параметра проставился source
        assertEquals(param1.getDataset().getSource().getId(), result.getParameters().get(0).getSourceId());
    }
}