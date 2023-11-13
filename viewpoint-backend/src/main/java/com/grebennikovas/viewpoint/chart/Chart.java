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
//@EqualsAndHashCode(callSuper = true)
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

        if (!Objects.equals(id, chart.id)) return false;
        if (!Objects.equals(name, chart.name)) return false;
        if (chartType != chart.chartType) return false;
        if (!Objects.equals(user, chart.user)) return false;
        if (!Objects.equals(dataset, chart.dataset)) return false;
        return Objects.equals(chartSettings, chart.chartSettings);
//        return Objects.equals(dashboards, chart.dashboards);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (chartType != null ? chartType.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (dataset != null ? dataset.hashCode() : 0);
        result = 31 * result + (chartSettings != null ? chartSettings.hashCode() : 0);
//        result = 31 * result + (dashboards != null ? dashboards.hashCode() : 0);
        return result;
    }
}
