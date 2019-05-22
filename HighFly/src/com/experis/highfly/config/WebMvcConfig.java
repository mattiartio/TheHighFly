package com.experis.highfly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.experis.highfly.*" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public WSData wsData() {
		return new WSData();  
	}
}
