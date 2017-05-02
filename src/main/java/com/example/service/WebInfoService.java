package com.example.service;


import com.example.repository.WebInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.model.*;
import java.util.*;

@Service
@Transactional
public class WebInfoService {
    @Autowired
    WebInfoRepository repository;

    public List<WebInfo> selectAll(){
        return repository.findAll();
    }
    public WebInfo save(WebInfo info){
        repository.save(info);
        return info;
    }
}
