package com.grebennikovas.viewpoint.chart;

import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.datasets.dto.DatasetDTO;
import com.grebennikovas.viewpoint.users.DTO.UserShortDTO;
import com.grebennikovas.viewpoint.users.User;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;

public class ChartDTO {

    private Long id;
    private String name;
    private ChartType chartType;
    private UserShortDTO user;
    private DatasetDTO dataset;
    private ChartSettings chartSettings;

    public ChartDTO() {
    }

    public ChartDTO(Chart chart) {
        this.id = chart.getId();
        this.name = chart.getName();
        this.chartType = chart.getChartType();
        this.user = new UserShortDTO(chart.getUser().getId(), chart.getUser().getUsername());
        this.dataset = new DatasetDTO();
        this.dataset.setId(chart.getDataset().getId());
        this.dataset.setName(chart.getDataset().getName());
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

    public UserShortDTO getUser() {
        return user;
    }

    public void setUser(UserShortDTO user) {
        this.user = user;
    }

    public DatasetDTO getDataset() {
        return dataset;
    }

    public void setDataset(DatasetDTO dataset) {
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

        ChartDTO chartDTO = (ChartDTO) o;

        if (!Objects.equals(id, chartDTO.id)) return false;
        if (!Objects.equals(name, chartDTO.name)) return false;
        if (chartType != chartDTO.chartType) return false;
        if (!Objects.equals(user, chartDTO.user)) return false;
        if (!Objects.equals(dataset, chartDTO.dataset)) return false;
        return Objects.equals(chartSettings, chartDTO.chartSettings);
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
