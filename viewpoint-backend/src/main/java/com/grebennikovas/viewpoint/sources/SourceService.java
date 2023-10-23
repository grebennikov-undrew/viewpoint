package com.grebennikovas.viewpoint.sources;

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
}
