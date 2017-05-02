package com.example.repository;

import com.example.model.WebInfo;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = WebInfo.class, idClass = Integer.class)
public interface WebInfoRepository {
    WebInfo save(WebInfo info);
    Iterable<WebInfo> findAll();
}
