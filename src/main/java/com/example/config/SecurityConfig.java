package com.example.config;

/**
 * Created by yuppy on 2017/04/15.
 */
public class SecurityConfig {
    private static SecurityConfig ourInstance = new SecurityConfig();

    public static SecurityConfig getInstance() {
        return ourInstance;
    }

    private SecurityConfig() {
    }
}
