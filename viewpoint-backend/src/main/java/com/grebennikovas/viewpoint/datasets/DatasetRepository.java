package com.grebennikovas.viewpoint.datasets;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DatasetRepository extends JpaRepository<Dataset, Long> {
    List<Dataset> findAll();
    Dataset save(Dataset person);
    Dataset getOne(Long id);
}
