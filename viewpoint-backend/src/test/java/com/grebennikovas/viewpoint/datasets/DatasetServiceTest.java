package com.grebennikovas.viewpoint.datasets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        Map<String,String> params = new HashMap<>();
        params.put("p_name","'Andrey'");
        ds.setSqlQuery(query);
        Parameter parameter = new Parameter();
        parameter.setName("p_name");
        parameter.setType("String");
        List<Parameter> parameters= new ArrayList<>();
        parameters.add(parameter);
        ds.setParameters(parameters);
        String actual = datasetService.prepareQuery(ds,params);
        String expected = "SELECT * FROM users WHERE name = 'Andrey' ";
        assertEquals(actual,expected);
    }
}