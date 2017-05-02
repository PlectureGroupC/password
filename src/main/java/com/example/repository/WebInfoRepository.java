package com.example.repository;

import com.example.model.WebInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

public interface WebInfoRepository extends JpaRepository<WebInfo, Integer>{
//    WebInfo save(WebInfo info);
//    Iterable<WebInfo> findAll();
}
