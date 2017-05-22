package com.example.domain.repository;

import com.example.domain.model.WebInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface WebInfoRepository extends JpaRepository<WebInfo, Integer>{
    List<WebInfo> findWebInfosByMailaddress(String mailaddress);
    WebInfo findWebInfoByNumber(Integer number);
}
