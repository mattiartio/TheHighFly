package com.experis.highfly.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.experis.highfly.dao.RoleDao;
import com.experis.highfly.entities.Role;
import com.experis.highfly.entities.Vehicle;


@Repository("roleDao")
@Scope(value = "prototype")
public class RoleDaoImpl extends GenericDaoImpl<Role> implements RoleDao {

	@Override
	public List<Role> listAllByType(String type) {
		
		List<Role> retList = null;
		
		Query q = em.createQuery("Select r from Role r where r.type=:tipo");
		
		q.setParameter("tipo", type);
		
		return retList;
	}
	
}
