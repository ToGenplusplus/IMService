package com.example.IMS.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.IMS.ApiResponse.ApiResponse;

@Configuration
public class ApiResponseConfiguration {
	
	@Bean
	public <T> ApiResponse<T> apiResponse()
	{
		return new ApiResponse<T>();
	}

}
