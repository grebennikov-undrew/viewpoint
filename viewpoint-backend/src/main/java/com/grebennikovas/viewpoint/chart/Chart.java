package com.grebennikovas.viewpoint.chart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grebennikovas.viewpoint.BaseEntity;
import com.grebennikovas.viewpoint.dashboard.Dashboard;
import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.users.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "charts")
@Getter
@Setter
@NoArgsConstructor
public class Chart {
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

    @ManyToMany(mappedBy = "charts", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Dashboard> dashboards;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chart chart = (Chart) o;

        return Objects.equals(id, chart.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
