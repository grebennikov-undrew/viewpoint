package com.grebennikovas.viewpoint.sources;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SourceRepository extends JpaRepository<Source, Long> {
    List<Source> findAll();
    Source save(Source person);
}