package com.grebennikovas.viewpoint.datasets;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DatasetRepository extends JpaRepository<Dataset, Long> {

    @EntityGraph(value = "Dataset.default")
    List<Dataset> findAll();
    Dataset save(Dataset person);
    @EntityGraph(value = "Dataset.default")
    Optional<Dataset> findById(Long id);
}
