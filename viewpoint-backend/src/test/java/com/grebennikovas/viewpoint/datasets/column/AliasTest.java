package com.grebennikovas.viewpoint.datasets.column;

import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.datasets.DatasetRepository;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.sources.SourceRepository;
import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.users.UserRepository;
import jakarta.persistence.Persistence;
import org.hibernate.annotations.Columns;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ColumnTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ColumnRepository columnRepository;
    @Autowired
    DatasetRepository datasetRepository;
    @Autowired
    SourceRepository sourceRepository;

    // Проверка ленивой загрузки
    // Проверка сравнения двух столбцов
    @Test
    void ColumnsLazyLoadTest() {
        Source s = new Source();
        s = sourceRepository.save(s);

        User u = new User();
        u.setUsername("test2");
        u.setEmail("test2@test.com");
        u.setPassword("123");
        u.setActive(true);
        u.setFirstname("test");
        u.setLastname("test");
        u = userRepository.save(u);

        Dataset d = new Dataset();
        d.setSource(s);
        d.setUser(u);
        d = datasetRepository.save(d);

        Column c = new Column();
        c.setDataset(d);
        c.setType("String");
        c.setName("Test column");

        Column savedColumn = columnRepository.save(c);
        long columnId = savedColumn.getId();
        Column foundColumn = columnRepository.findById(columnId).get();

        boolean result = Persistence.getPersistenceUtil().isLoaded(foundColumn,"dataset");

        assertNotSame(savedColumn, foundColumn);
        assertFalse(result);
        assertEquals(savedColumn, foundColumn);
        assertEquals(savedColumn.hashCode(), foundColumn.hashCode());
    }

    // Проверка обновления списка столбцов при обновлении датасета
    @Test
    void ColumnsUniqueUpsertTestTest() {
        Source s = new Source();
        s = sourceRepository.save(s);

        User u = new User();
        u.setUsername("test");
        u.setEmail("test@test.com");
        u.setPassword("123");
        u.setActive(true);
        u.setFirstname("test");
        u.setLastname("test");
        u = userRepository.save(u);

        Dataset d = new Dataset();
        d.setSource(s);
        d.setUser(u);
        d = datasetRepository.save(d);

        Column c1 = new Column();
        c1.setDataset(d);
        c1.setType("String");
        c1.setName("Must be removed");

        Column c2 = new Column();
        c2.setDataset(d);
        c2.setType("Double");
        c2.setName("Keep cool");

        Column c3 = new Column();
        c3.setDataset(d);
        c3.setType("String");
        c3.setName("Must exist");

        Column c4 = new Column();
        c4.setDataset(d);
        c4.setType("Double");
        c4.setName("Keep cool");

        d.setColumns(Set.of(c1,c2));
        datasetRepository.save(d);

        d.setColumns(Set.of(c2,c3,c4));
        datasetRepository.save(d);

        Set<Column> dsColumns = columnRepository.findAllByDatasetId(d.getId());
        int columnCount = dsColumns.size();
        boolean firstInResult = dsColumns.contains(c1);
        boolean thirdInResult = dsColumns.contains(c3);
        boolean noDuplicates = dsColumns.contains(c2) || dsColumns.contains(c4);

        assertEquals(columnCount, 3);
        assertFalse(firstInResult);
        assertTrue(thirdInResult);
        assertTrue(noDuplicates);
    }

}