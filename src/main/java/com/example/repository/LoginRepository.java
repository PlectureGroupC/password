package com.example.repository;

import com.example.model.Login;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Login.class, idClass = Integer.class)
public interface LoginRepository {
    Login save(Login login);
}
