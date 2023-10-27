package com.grebennikovas.viewpoint.dashboard;

import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.datasets.DatasetRepository;
import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterRepository;
import com.grebennikovas.viewpoint.datasets.results.Entry;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.sources.connections.ConnectionFactory;
import com.grebennikovas.viewpoint.sources.connections.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<String> getFilterValues(Long id) {
        Parameter p = parameterRepository.findById(id).get();
        Dataset ds = p.getDataset();
        Source src = ds.getSource();
        Executable dbInstance = ConnectionFactory.connect(src);
        Result result = dbInstance.execute(p.getSqlQuery());
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
