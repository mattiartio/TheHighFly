/**
 * DatabaseService.java
 *
 * robgion
 * www.2clever.it
 * 
 * 05 lug 2017
 * For further information please write to info@2clever.it
 */
package com.experis.highfly.services;

import javax.persistence.EntityManager;

public interface JpaService {

	public EntityManager getDatabaseConnection();
	
	public void terminateService();
}
