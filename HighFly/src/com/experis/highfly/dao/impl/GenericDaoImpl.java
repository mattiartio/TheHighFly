package com.experis.highfly.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.experis.highfly.dao.GenericDao;
import com.experis.highfly.services.JpaService;


public class GenericDaoImpl<T> implements GenericDao<T> {
	@Autowired
	@Qualifier(value = "jpaService")
	private JpaService jpaService;

	protected EntityManager em;

	private Class<T> type;

	@PostConstruct
	@Override
	public void init() {

		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];

		this.em = jpaService.getDatabaseConnection();
	}

	@Override
	public T insert(final T t) {
		this.em.persist(t);
		return t;
	}

	@Override
	public void delete(final Object id) {
		this.em.remove(this.em.getReference(type, id));
	}

	@Override
	public T find(final Object id) {
		return (T) this.em.find(type, id);
	}

	@Override
	public T update(final T t) {
		return this.em.merge(t);
	}
	
	@Override
	public List<T> findAll(){
		
		Query q = em.createQuery("select o from " + type + " o");
		
		return q.getResultList();
		
		
	}
}