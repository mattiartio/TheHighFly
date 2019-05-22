package com.experis.highfly.messages;

public enum ResponseStatus
{
	OK(1, "Successful operation"),
	
	USER_NOT_FOUND(2,"User not found"),
	
	INTERNAL_SERVER_ERROR(3, "Internal server error");
	
	
	
	
	
	private final int value;
	private final String description;
	
	ResponseStatus(int value, String description){
		this.value = value;
		this.description = description;
	}
	
	/**
	 * Return the integer value of this status code.
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Return the reason phrase of this status code.
	 */
	public String getDescription() {
		return this.description;
	}
	
}
