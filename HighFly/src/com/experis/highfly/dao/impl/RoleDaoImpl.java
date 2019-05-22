package com.experis.highfly.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.experis.highfly.dao.RoleDao;
import com.experis.highfly.entities.Role;


@Repository("roleDao")
@Scope(value = "prototype")
public class RoleDaoImpl extends GenericDaoImpl<Role> implements RoleDao {

	@Override
	public Role findByType(String type) {
		
		Query q = em.createQuery("Select r from Role r where r.type=:tipo");
		
		q.setParameter("tipo", type);
		
		return (Role)q.getResultList().get(0);
	}
	
}
