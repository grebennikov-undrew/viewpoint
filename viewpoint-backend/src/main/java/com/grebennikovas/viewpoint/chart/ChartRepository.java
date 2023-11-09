package com.grebennikovas.viewpoint.chart;

import com.grebennikovas.viewpoint.datasets.Dataset;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChartRepository extends JpaRepository<Chart, Long> {

    List<Chart> findAll();

    Chart save(Chart chart);

    Chart getOne(Long id);

    Optional<Chart> findById(Long id);
}
