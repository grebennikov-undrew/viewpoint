package com.grebennikovas.viewpoint.datasets.parameter;

import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.datasets.column.Column;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    List<Parameter> findAll();
    Optional<Parameter> findById(Long id);
    List<Parameter> findAllByDataset_id(Long Id);
    Dataset save(Dataset person);
}
