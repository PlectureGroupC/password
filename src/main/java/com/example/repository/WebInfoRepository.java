package com.example.domain.repository;

import com.example.domain.model.WebInfo;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = WebInfo.class, idClass = Integer.class)
public interface WebInfoRepository {
    WebInfo save(WebInfo info);
    Iterable<WebInfo> findAll();
}
