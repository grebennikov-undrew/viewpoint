package com.grebennikovas.viewpoint.datasets;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class DatasetDTO {
    private Long id;
    private String name;
    private String sqlQuery;
    private String username;
    private DatasetUserDTO user;
    private Long source_id;
    private List<DatasetColumnDTO> columns;
    private List<DatasetParameterDTO> parameters;
    private Timestamp createdOn;
    private Timestamp updatedOn;
}

class DatasetUserDTO {
    private Long id;
    private String username;
}

class DatasetParameterDTO {
    private Long id;
    private String name;
    private String type;
}

class DatasetColumnDTO {
    private Long id;
    private String name;
    private String type;
}