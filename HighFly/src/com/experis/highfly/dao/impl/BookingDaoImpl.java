package com.experis.highfly.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.experis.highfly.dao.BookingDao;
import com.experis.highfly.entities.Booking;
import com.experis.highfly.utils.BookingFilter;

@Repository("bookingDao")
@Scope(value = "prototype")

public class BookingDaoImpl extends GenericDaoImpl<Booking> implements BookingDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Booking> findBookingByFilters(BookingFilter bookingFilter) {

		boolean condition = false;

		List<Booking> booking = null;
		try {
			String query = "select b from Booking b join b.client u join b.transport t join t.vehicle v";

			if (bookingFilter.getName() != null && !bookingFilter.getName().isEmpty()) {
				condition = true;
				query += " where u.name = :nam ";
			}
			if (bookingFilter.getSurname() != null && !bookingFilter.getSurname().isEmpty()) {
				if (condition) {
					query += " and u.surname = :sur";
				} else {
					condition = true;
					query += " where u.surname = :sur";
				}
			}
			if (bookingFilter.getVehicle() != null && !bookingFilter.getVehicle().isEmpty()) {

				if (condition) {
					query += " and v.type = :vehic";
				} else {
					condition = true;
					query += " where v.type = :vehic";
				}
			}
			if (bookingFilter.getCompany() != null && !bookingFilter.getCompany().isEmpty()) {
				if (condition) {
					query += " and u.company = :comp";
				} else {
					condition = true;
					query += " where u.company = :comp";
				}
			}
			if (bookingFilter.getDateTo() != null) {
				if (condition) {
					query += " and b.dateTo = :datTo";
				} else {
					condition = true;
					query += " where b.dateTo = :datTo";
				}
			}
			if (bookingFilter.getDateFrom() != null) {
				if (condition) {
					query += " and b.dateFrom = :datFrom";
				} else {
					condition = true;
					query += " where b.dateFrom = :datFrom";
				}
			}
			if ((Integer) bookingFilter.getUserId() != null && (Integer) bookingFilter.getUserId() != 0) {
				if (condition) {
					query += " and u.id = :user";
				} else {
					condition = true;
					query += " where u.id = :user";
				}
			}
			if ((Integer)bookingFilter.getTransportId() != null && (Integer)bookingFilter.getTransportId() != 0) {
				if (condition) {
					query += " and t.id = :transport";
				} else {
					condition = true;
					query += " where t.id = :transport";
				}
			}

			Query q = em.createQuery(query);

			if (bookingFilter.getName() != null && !bookingFilter.getName().isEmpty()) {
				q.setParameter("nam", bookingFilter.getName());
			}
			if (bookingFilter.getSurname() != null && !bookingFilter.getSurname().isEmpty()) {
				q.setParameter("sur", bookingFilter.getSurname());
			}
			if (bookingFilter.getVehicle() != null && !bookingFilter.getVehicle().isEmpty()) {
				q.setParameter("vehic", bookingFilter.getVehicle());
			}
			if (bookingFilter.getCompany() != null && !bookingFilter.getCompany().isEmpty()) {
				q.setParameter("comp", bookingFilter.getCompany());
			}
			if (bookingFilter.getDateTo() != null) {
				q.setParameter("datTo", bookingFilter.getDateTo());
			}
			if (bookingFilter.getDateFrom() != null) {
				q.setParameter("datFrom", bookingFilter.getDateFrom());
			}
			if ((Integer)bookingFilter.getUserId() != null && (Integer) bookingFilter.getUserId() != 0) {
				q.setParameter("user", bookingFilter.getUserId());
			}
			if ((Integer)bookingFilter.getTransportId() != null && (Integer)bookingFilter.getTransportId() != 0) {
				q.setParameter("transport", bookingFilter.getTransportId());
			}


			booking = (List<Booking>)q.getResultList();
			
			
		} finally {
			em.close();
		}
		return booking;
	}
}
