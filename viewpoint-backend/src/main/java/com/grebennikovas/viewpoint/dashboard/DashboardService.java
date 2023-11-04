package com.grebennikovas.viewpoint.dashboard;

import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.datasets.DatasetRepository;
import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterRepository;
import com.grebennikovas.viewpoint.datasets.results.Entry;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.sources.SourceRepository;
import com.grebennikovas.viewpoint.sources.SourceService;
import com.grebennikovas.viewpoint.sources.connections.ConnectionFactory;
import com.grebennikovas.viewpoint.sources.connections.DbConnection;
import com.grebennikovas.viewpoint.utils.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DashboardService {
    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    SourceService sourceService;

    public List<String> getFilterValues(Long id) throws SQLException {
        Parameter p = parameterRepository.findById(id).get();
        Dataset ds = p.getDataset();
        return getFilterValues(ds.getSource().getId(), p.getSqlQuery());
    }

    public List<String> getFilterValues(Long sourceId, String sqlQuery) throws SQLException {
        Result column = sourceService.execute(sourceId,sqlQuery);
        List<String> filterOptions = SqlUtils.getFilterValues(column);
        return filterOptions;
    }
}
