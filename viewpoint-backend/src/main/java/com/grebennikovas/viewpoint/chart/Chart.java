package com.grebennikovas.viewpoint.chart;

import com.grebennikovas.viewpoint.BaseEntity;
import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.users.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaTypeRegistration;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.ConvertedBasicCollectionType;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.jdbc.JsonAsStringJdbcType;

import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "charts")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Chart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ChartType chartType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dataset_id")
    private Dataset dataset;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private ChartSettings chartSettings;
}
