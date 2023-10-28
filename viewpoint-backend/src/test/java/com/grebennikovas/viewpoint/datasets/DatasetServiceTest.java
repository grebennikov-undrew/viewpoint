package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
class DatasetServiceTest {
    @Test
    void prepareQuery() {
        DatasetService datasetService = new DatasetService();
        Dataset ds = new Dataset();
        String query = "SELECT * FROM users WHERE name ={:p_name}";
        Map<String,String> paramValues = new HashMap<>();
        paramValues.put("p_name","Andrey");
        ds.setSqlQuery(query);
        Parameter parameter = new Parameter();
        parameter.setName("p_name");
        parameter.setType("String");
        List<Parameter> parameters= new ArrayList<>();
        parameters.add(parameter);
        String actual = datasetService.prepareQuery(query,parameters,paramValues);
        String expected = "SELECT * FROM users WHERE name = 'Andrey' ";
        assertEquals(actual,expected);
    }
}