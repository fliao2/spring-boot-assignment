package com.assignment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyAppUserDetailsService myAppUserDetailsService;
	
	@Autowired
	private AppAuthenticationEntryPoint appAuthenticationEntryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		    .authorizeRequests()
		  	.antMatchers("/assignment/**").hasAnyRole("ADMIN","USER")
			.and().httpBasic().realmName("MY APP REALM")
			.authenticationEntryPoint(appAuthenticationEntryPoint);
	}
	
    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	ShaPasswordEncoder sha = new ShaPasswordEncoder();

    	// This is left in here only to show how the passwords were generated.
    	/*
    	String password = "pfliao1";
    	String encodedPassword = sha.encodePassword(password,null);
    	System.out.println("fliao1 password " + encodedPassword);
    	
    	password = "pfliao2";
    	encodedPassword = sha.encodePassword(password,null);
    	System.out.println("fliao2 password " + encodedPassword);
    	 */
    	
        auth.userDetailsService(myAppUserDetailsService).passwordEncoder(sha);
	}
}
