package com.experis.highfly.entities.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.experis.highfly.dao.GenericDao;
import com.opengest.jpa.utils.JpaUtils;

public class GenericDaoImpl<T> implements GenericDao<T>
{
	protected EntityManager em;

	protected EntityTransaction trx = null;

	private Class<T> type;

	@Override
	public void init()
	{

		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];

		this.em = JpaUtils.getInstance().getEntityManager();
	}

	@Override
	public T insert(final T t)
	{
		this.em.persist(t);
		return t;
	}

	@Override
	public void delete(final Object id)
	{
		this.em.remove(this.em.getReference(type, id));
	}

	@Override
	public T find(final Object id)
	{
		return (T) this.em.find(type, id);
	}

	@Override
	public T update(final T t)
	{
		return this.em.merge(t);
	}
}
