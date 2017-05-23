package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.domain.service.AccountUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AccountUserDetailsService accountUserDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**", "/css/**", "/javascript/**", "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
        	.disable()
        	.authorizeRequests()
            .antMatchers("/index","/account/create","/resetPass/**", "/forgot", "/reset")
            .permitAll()
            .antMatchers("/**")
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/loginForm")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/home", true)
            .usernameParameter("username")
            .passwordParameter("password")
            .failureUrl("/loginForm?error")
            .permitAll()
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
            .logoutSuccessUrl("/loginForm");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    	auth.userDetailsService(accountUserDetailsService).passwordEncoder(passwordEncoder());
    }
}