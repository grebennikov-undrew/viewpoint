package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.datasets.results.Row;
import com.grebennikovas.viewpoint.sources.DatabaseType;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.sources.connections.ConnectionFactory;
import com.grebennikovas.viewpoint.sources.connections.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@Service
public class DatasetService {
    @Autowired
    DatasetRepository datasetRepository;

    public List<Dataset> findAll() {
        return datasetRepository.findAll();
    }
    public Dataset save(Dataset dataset) {
        return datasetRepository.save(dataset);
    }
    public Result execute(Long id, Map<String,String> params) {
        Dataset ds = datasetRepository.getOne(id);
        Source src = ds.getSource();
        Executable dbInstance = ConnectionFactory.connect(src);
        Result result = dbInstance.execute(ds.getSqlQuery(),params);
        return result;
    }
}
