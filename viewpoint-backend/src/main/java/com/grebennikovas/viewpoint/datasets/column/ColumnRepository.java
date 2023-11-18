package com.grebennikovas.viewpoint.datasets.column;
import com.grebennikovas.viewpoint.datasets.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ColumnRepository extends JpaRepository<Column, Long> {

    List<Column> findAll();

    Optional<Column> findById(Long id);

    Dataset save(Dataset person);

    Set<Column> findAllByDatasetId(Long id);

    Set<Column> findAllByDatasetIdIn(Set<Long> ids);

    int countByDatasetId(Long datasetId);

    @Transactional
    void deleteByDatasetId(Long datasetId);

}