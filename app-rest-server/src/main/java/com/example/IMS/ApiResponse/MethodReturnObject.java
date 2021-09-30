package com.example.IMS.ApiResponse;

public class MethodReturnObject<T> {
	
	private String returnMessage;
	
	private T returnObject;

	public MethodReturnObject() {}
	
	
	public static <T> MethodReturnObject<T> of(T objToReturn)
	{
		MethodReturnObject<T> ret = new MethodReturnObject<>();
		ret.setReturnObject(objToReturn);
		ret.setReturnMessage(null);
		return ret;
	}
	public static <T> MethodReturnObject<T> of(String returnMessage, T objToReturn)
	{
		MethodReturnObject<T> ret = new MethodReturnObject<>();
		ret.setReturnObject(objToReturn);
		ret.setReturnMessage(returnMessage);
		return ret;
	}
	
	public static <T> MethodReturnObject<T> of(String returnMessage)
	{
		MethodReturnObject<T> ret = new MethodReturnObject<>();
		ret.setReturnObject(null);
		ret.setReturnMessage(returnMessage);
		return ret;
	}


	public String getReturnMessage() {
		return returnMessage;
	}


	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}


	public T getReturnObject() {
		return returnObject;
	}


	public void setReturnObject(T returnObject) {
		this.returnObject = returnObject;
	}
	

	
}
