package com.experis.highfly.messages;

public class ResponseMessage
{
	private ResponseStatus responseStatus;
	
	private String message;

	private Object data;

	public ResponseStatus getResponseStatus()
	{
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus)
	{
		this.responseStatus = responseStatus;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}
	
	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
	
	
}
