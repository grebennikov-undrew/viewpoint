package com.grebennikovas.viewpoint.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardRequestDto {

    private Long id;
    private String name;
    private String description;
    private List<Long> chartsId;
    private String layout;

}
