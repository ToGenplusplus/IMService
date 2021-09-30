package com.example.IMS.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.IMS.ApiResponse.ApiResponse;
import com.example.IMS.ApiResponse.MethodReturnObject;

@Configuration
public class ApiConfiguration {
	
	@Bean
	public <T> ApiResponse<T> apiResponse()
	{
		return new ApiResponse<T>();
	}
	
	@Bean
	public <T> MethodReturnObject<T> methodReturnObject()
	{
		return new MethodReturnObject<T>();
	}

}
