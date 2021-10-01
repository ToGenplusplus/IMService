package com.example.IMS.ApiResponse;


public class ApiResponse<T> {
	
	private boolean success;
	
	private String message;
	
	private T data;
	
	public ApiResponse(){}
	
	
	public static <T> ApiResponse<T> success(T data)
	{
		ApiResponse<T> response = new ApiResponse<>();
		response.setSuccess(true);
		response.setData(data);
		response.setMessage(null);
		
		return response;
	}
	
	public static <T> ApiResponse<T> failure(String message)
	{
		ApiResponse<T> response = new ApiResponse<>();
		response.setSuccess(false);
		response.setData(null);
		response.setMessage(message);
		
		return response;
		
	}



	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public T getData() {
		return data;
	}


	public void setData(T data) {
		this.data = data;
	}
	
	
	
	

}
