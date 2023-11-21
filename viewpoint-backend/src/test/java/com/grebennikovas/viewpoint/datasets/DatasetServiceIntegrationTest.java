package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.ViewpointApplication;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.sources.SourceRepository;
import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.users.UserRepository;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import com.grebennikovas.viewpoint.datasets.column.Column;
import com.grebennikovas.viewpoint.datasets.column.ColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

//@ContextConfiguration(classes=ViewpointApplication.class)
@SpringBootTest
public class DatasetServiceIntegrationTest {
    @Autowired
    private ColumnRepository columnRepository;
    @Autowired
    private DatasetRepository datasetRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SourceRepository sourceRepository;
    @Autowired
    private DatasetService datasetService;
    @Autowired
    DatasetMapper datasetMapper;

    // Проверка ленивой загрузки Dataset
    @Test
    public void testLazyEagerLoading() {
        User u = new User();
        u = userRepository.save(u);
        Source s = new Source();
        s = sourceRepository.save(s);
        // Создание датасета
        Dataset dataset = new Dataset();
        dataset.setName("Test");
        dataset.setUser(u);
        dataset.setSource(s);

        Column c = new Column();
        c.setDataset(dataset);
        c.setType("String");
        c.setName("Test column");
        dataset.setColumns(Set.of(c));

        Dataset saved = datasetRepository.save(dataset);
        List<Dataset> foundAll = datasetRepository.findAll();
        int foundIndex = foundAll.indexOf(saved);
        Dataset found = foundAll.get(foundIndex);

        DatasetDto dsDto = datasetMapper.toShortDto(found);

        boolean columnsLoaded = Persistence.getPersistenceUtil().isLoaded(found,"columns");
        boolean userLoaded = Persistence.getPersistenceUtil().isLoaded(found,"user");
        boolean sourceLoaded = Persistence.getPersistenceUtil().isLoaded(found,"source");

        // Columns assert
        assertNotSame(saved, found);
        assertFalse(columnsLoaded);
        assertEquals(saved, found);
        assertEquals(saved.hashCode(), found.hashCode());
        assertNull(dsDto.getColumnsDto());
        // Source assert
        assertTrue(sourceLoaded);
        assertNotNull(dsDto.getSourceDto());
        // User assert
        assertTrue(userLoaded);
        assertNotNull(dsDto.getUser());
    }

    // Проверка полной загрузки Dataset
    @Test
    @Transactional
    public void testEagerLoading() {
        User u = new User();
        u = userRepository.save(u);
        Source s = new Source();
        s = sourceRepository.save(s);
        // Создание датасета
        Dataset dataset = new Dataset();
        dataset.setName("Test");
        dataset.setUser(u);
        dataset.setSource(s);

        Column c = new Column();
        c.setDataset(dataset);
        c.setType("String");
        c.setName("Test column");
        dataset.setColumns(Set.of(c));

        Dataset saved = datasetRepository.save(dataset);
        List<Dataset> foundAll = datasetRepository.findAll();
        int foundIndex = foundAll.indexOf(saved);
        Dataset found = foundAll.get(foundIndex);

        DatasetDto dsDto = datasetMapper.toDto(found);

        boolean columnsLoaded = Persistence.getPersistenceUtil().isLoaded(found,"columns");
        boolean userLoaded = Persistence.getPersistenceUtil().isLoaded(found,"user");
        boolean sourceLoaded = Persistence.getPersistenceUtil().isLoaded(found,"source");

        // Columns assert
        assertTrue(columnsLoaded);
        assertNotNull(dsDto.getColumnsDto());
        // Source assert
        assertTrue(sourceLoaded);
        assertNotNull(dsDto.getSourceDto());
        // User assert
        assertTrue(userLoaded);
        assertNotNull(dsDto.getUser());
    }

    // Обновление колонок с только новым списком (актуально для новых датасетов)
    @Test
    public void testUpsertColumnsOnlyNew() {
        User u = new User();
        u.setId(1L);
        u.setUsername("test_un");

        Source s = new Source();
        s.setId(1L);
        s.setName("test_db");

        // Создание датасета
        Dataset dataset = new Dataset();
        dataset.setName("Test Dataset");
        dataset.setUser(u);
        dataset.setSource(s);
        dataset = datasetRepository.save(dataset);

        // Создание колонок для датасета
        List<Column> newColumns = new ArrayList<>();
        Column column1 = new Column();
        column1.setName("Column 1");
        column1.setType("Type 1");
        column1.setDataset(dataset);
        newColumns.add(column1);

        Column column2 = new Column();
        column2.setName("Column 2");
        column2.setType("Type 2");
        column2.setDataset(dataset);
        newColumns.add(column2);

//        // Вызов метода
//        List<Column> result = datasetService.upsertColumns(newColumns, dataset);
//
//        // Проверка
//        assertNotNull(result);
//        // Проверка на количество
//        assertEquals(2, result.size());
//        // Проверка, что все значения сохранились в БД
//        Set<Column> savedColumns = columnRepository.findAllByDataset_id(dataset.getId());
//        assertEquals(2, savedColumns.size());
//        // Проверки на значения
//        assertEquals("Column 1", savedColumns.get(0).getName());
//        assertEquals("Type 1", savedColumns.get(0).getType());
//        assertEquals("Column 2", savedColumns.get(1).getName());
//        assertEquals("Type 2", savedColumns.get(1).getType());
    }
}

