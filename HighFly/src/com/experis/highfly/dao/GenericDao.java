package com.experis.highfly.dao;

public interface GenericDao<T>
{

	void init();

	T insert(T t);

	void delete(Object id);

	T find(Object id);

	T update(T t);

}