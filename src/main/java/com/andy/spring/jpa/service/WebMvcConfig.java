package com.andy.spring.jpa.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebMvcConfig extends WebMvcAutoConfiguration {
	
	@Bean
	public MessageDigest messageDigest() {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			return md;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
