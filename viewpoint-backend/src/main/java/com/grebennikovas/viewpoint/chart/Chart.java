package com.grebennikovas.viewpoint.chart;

import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.users.User;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.ConvertedBasicCollectionType;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.jdbc.JsonAsStringJdbcType;

import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "charts")
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
    @Column(columnDefinition = "jsonb")
    private ChartSettings chartSettings;

    public Chart() {
    }

    public Chart(Long id, String name, ChartType chartType, User user, Dataset dataset, ChartSettings chartSettings) {
        this.id = id;
        this.name = name;
        this.chartType = chartType;
        this.user = user;
        this.dataset = dataset;
        this.chartSettings = chartSettings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public ChartSettings getChartSettings() {
        return chartSettings;
    }

    public void setChartSettings(ChartSettings chartSettings) {
        this.chartSettings = chartSettings;
    }

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
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (chartType != null ? chartType.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (dataset != null ? dataset.hashCode() : 0);
        result = 31 * result + (chartSettings != null ? chartSettings.hashCode() : 0);
        return result;
    }
}
