package com.experis.highfly.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "booking")
public class Booking implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7787264523428320573L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "booking_id")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User client;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transport_id", nullable = false)
	private Transport transport;

	@Column(name = "booking_date_from", nullable = false)
	private Date dateFrom;

	@Column(name = "booking_date_to", nullable = false)
	private Date dateTo;

	@Column(name = "booking_price_tot", nullable = false)
	private float priceTot;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public float getPriceTot() {
		return priceTot;
	}

	public void setPriceTot(float priceTot) {
		this.priceTot = priceTot;
	}

}
