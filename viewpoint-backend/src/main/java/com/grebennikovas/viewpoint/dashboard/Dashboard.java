package com.grebennikovas.viewpoint.dashboard;

import com.grebennikovas.viewpoint.BaseEntity;
import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartSettings;
import com.grebennikovas.viewpoint.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "dashboards")
@Data
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = true)
public class Dashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "dashboard_chart",
            joinColumns = @JoinColumn(name = "dashboard_id"),
            inverseJoinColumns = @JoinColumn(name = "chart_id")
    )
    private List<Chart> charts;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private String layout;
}
