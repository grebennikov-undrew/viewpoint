package com.grebennikovas.viewpoint.datasets.column;
import com.grebennikovas.viewpoint.datasets.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ColumnRepository extends JpaRepository<Column, Long> {
    List<Column> findAll();
    List<Column> findAllByDataset_id(Long id);
    Optional<Column> findById(Long id);
    Dataset save(Dataset person);
}