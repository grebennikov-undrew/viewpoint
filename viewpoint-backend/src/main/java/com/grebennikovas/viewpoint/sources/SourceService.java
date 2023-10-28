package com.grebennikovas.viewpoint.sources;

import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.connections.ConnectionFactory;
import com.grebennikovas.viewpoint.sources.connections.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceService {
    @Autowired
    SourceRepository sourceRepository;

    public List<Source> findAll(){
        return sourceRepository.findAll();
    }
    public Source check_and_save(Source source){
        return sourceRepository.save(source);
    }
    public Result execute(Long sourceId, String query) {
        Source src = sourceRepository.findById(sourceId).get();
        Executable dbInstance = ConnectionFactory.connect(src);
        return dbInstance.execute(query);
    }
}
