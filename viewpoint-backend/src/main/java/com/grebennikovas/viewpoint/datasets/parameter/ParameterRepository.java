package com.grebennikovas.viewpoint.datasets.parameter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    List<Parameter> findAll();
    Optional<Parameter> findById(Long id);
    List<Parameter> findAllByDataset_id(Long Id);
//    List<Parameter> saveAll(List<Parameter> parameters);
    void deleteById(Long Id);
}
