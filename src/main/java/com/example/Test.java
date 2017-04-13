package com.example;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yuppy on 2017/04/13.
 */
@RestController
class Test {

    @RequestMapping("/hi/{name}")
    String hi(@PathVariable String name){
        if(name.equals("hello")){
            return "Hello";
        }
        else{
            return name;
        }
    }
}