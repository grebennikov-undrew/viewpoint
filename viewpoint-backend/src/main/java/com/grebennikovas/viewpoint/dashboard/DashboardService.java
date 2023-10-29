package com.grebennikovas.viewpoint.dashboard;

import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.datasets.DatasetRepository;
import com.grebennikovas.viewpoint.datasets.dto.ParameterDTO;
import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterRepository;
import com.grebennikovas.viewpoint.datasets.results.Entry;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.sources.SourceRepository;
import com.grebennikovas.viewpoint.sources.SourceService;
import com.grebennikovas.viewpoint.sources.connections.ConnectionFactory;
import com.grebennikovas.viewpoint.sources.connections.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DashboardService {
    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    DatasetRepository datasetRepository;
    @Autowired
    SourceRepository sourceRepository;

    public List<String> getFilterValues(Long id) throws SQLException {
        Parameter p = parameterRepository.findById(id).get();
        Dataset ds = p.getDataset();
        return getFilterValues(ds.getSource().getId(), p.getSqlQuery());
    }

    public List<String> getFilterValues(Long sourceId, String sqlQuery) throws SQLException {
        Source src = sourceRepository.findById(sourceId).get();
        Executable dbInstance = ConnectionFactory.connect(src);
        Result result = dbInstance.execute(sqlQuery);
        List<String> filterOptions = new ArrayList<>();
        result.getRows().forEach(r -> {
            Optional<Entry> entry = r.getEntries().values().stream().findFirst();
            if (entry.isPresent()) {
                filterOptions.add(entry.get().getValue().toString());
            }
        });
        return filterOptions;
    }
}
