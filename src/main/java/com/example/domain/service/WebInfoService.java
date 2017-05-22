package com.example.domain.service;


import com.example.domain.model.WebInfo;
import com.example.domain.repository.WebInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class WebInfoService {
    @Autowired
    WebInfoRepository repository;

    public List<WebInfo> findAll(){
        return repository.findAll();
    }
    public WebInfo save(WebInfo info){
        return repository.save(info);
    }
    public WebInfo update(WebInfo info){
        return repository.save(info);
    }
    public void delete(WebInfo info){
        repository.delete(info);
    }
    public List<WebInfo> findWebInfosByMailaddress(String mailaddress){
        List<WebInfo> infos = repository.findWebInfosByMailaddress(mailaddress);
        return infos;
    }
    public WebInfo findWebInfoByNumber(Integer number){
        WebInfo info = repository.findWebInfoByNumber(number);
        return info;
    }
}
