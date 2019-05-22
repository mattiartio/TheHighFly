package com.experis.highfly.viewbeans;

import java.sql.Date;

public class BookingViewBean {
	private String username;
	private String name;
	private String surname;
	private TransportViewBean transportViewBean;
	private Date dataFrom;
	private Date dataTo;
	private Float price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public TransportViewBean getTransportViewBean() {
		return transportViewBean;
	}

	public void setTransportViewBean(TransportViewBean transportViewBean) {
		this.transportViewBean = transportViewBean;
	}

	public Date getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(Date dataFrom) {
		this.dataFrom = dataFrom;
	}

	public Date getDataTo() {
		return dataTo;
	}

	public void setDataTo(Date dataTo) {
		this.dataTo = dataTo;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
