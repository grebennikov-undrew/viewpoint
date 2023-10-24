package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.results.Entry;
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
import java.util.HashMap;
import java.util.LinkedList;
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
    public Result execute(Long id, Map<String,String> params) throws ClassNotFoundException {
        Dataset ds = datasetRepository.getOne(id);
        Source src = ds.getSource();
        Executable dbInstance = ConnectionFactory.connect(src);
        List<Map<String, Object>> raw_rows = dbInstance.execute(ds.getSqlQuery(),params);
        Result result = new Result();
        List<Row> rows = new LinkedList();
        for (Map<String, Object> raw_row : raw_rows) {
            Row row = new Row();
            Map<String, Entry> entries = new HashMap();
            for (Map.Entry<String, Object> raw_entry : raw_row.entrySet()) {
                String columnName = raw_entry.getKey();
                String javaColType = coltypes.get(columnName);
                if (javaColType.equals("String")) {
                    Entry<String> entry = new Entry();
                    String a = raw_entry.getValue();
                    entries.put(columnName,entry);
                }
                if (javaColType.equals("Double")) {
                    Entry<Double> entry = new Entry();
                    entry.setValue(rs.getDouble(i));
                    entries.put(columnName,entry);
                }
                if (javaColType.equals("Timestamp")) {
                    Entry<Timestamp> entry = new Entry();
                    entry.setValue(rs.getTimestamp(i));
                    entries.put(columnName, entry);
                }
                if (javaColType.equals("Boolean")) {
                    Entry<Boolean> entry = new Entry();
                    entry.setValue(rs.getBoolean(i));
                    entries.put(columnName, entry);
                }
                if (javaColType.equals("Object")) {
                    Entry<Object> entry = new Entry();
                    entry.setValue(rs.getObject(i));
                    entries.put(columnName, entry);
                }
            }
            row.setEntries(entries);
            rows.add(row);
        }
        result.setRows(rows);
        result.setColtypes(coltypes);
        return result;
        return result;
    }
}
