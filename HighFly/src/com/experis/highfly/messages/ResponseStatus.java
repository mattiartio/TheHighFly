package com.experis.highfly.messages;

public enum ResponseStatus
{
	OK(1, "Successful operation"),
	
	USER_NOT_FOUND(2,"User not found"),
	
	INTERNAL_SERVER_ERROR(3, "Internal server error"),
	
	JSON_ERROR(4, "Json composition error"),
	
	LIST_NOT_FOUND(5, "Booking list not found"),
	
	BOOKING_NOT_FOUND(6, "Booking not found"),
	
	DUPLICATE_RECORD(7, "Record already in database"),
	
	TRANSPORT_NOT_FOUND(8, "Transport not found"),
	
	DATE_ERROR(9, "Dates inconsistent");
	
	
	
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
