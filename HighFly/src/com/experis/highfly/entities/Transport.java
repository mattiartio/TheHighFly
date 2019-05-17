package com.experis.highfly.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "transport")
public class Transport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 660860109347015304L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "transport_id")
	private int id;

	@Column(name = "max_seats")
	private int maxSeats;

	@Column(name = "price")
	private float price;

	@OneToMany(mappedBy = "transport")
	private List<Booking> bookings;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transport_type")
	private Vehicle type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMaxSeats() {
		return maxSeats;
	}

	public void setMaxSeats(int maxSeats) {
		this.maxSeats = maxSeats;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public Vehicle getType() {
		return type;
	}

	public void setType(Vehicle type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
