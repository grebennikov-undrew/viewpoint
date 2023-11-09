package com.grebennikovas.viewpoint.datasets.parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grebennikovas.viewpoint.BaseEntity;
import com.grebennikovas.viewpoint.datasets.Dataset;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Objects;

@Entity
@Table(name = "parameters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dataset_id", nullable = false)
    private Dataset dataset;

    private String name;

    private String type;

    private String sqlQuery;


}
