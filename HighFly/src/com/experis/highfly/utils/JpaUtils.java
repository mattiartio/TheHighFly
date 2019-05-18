package com.experis.highfly.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//Questa classe penso sia da togliere perchè adesso ci sarebbe jpaService

public class JpaUtils {

	private static JpaUtils _instance = null;
	
	private EntityManagerFactory emfactory = null;
	
	
	/**
	 * Costruttore private per l'implementazione del pattern Singleton
	 */
	private JpaUtils() {
		
		emfactory = Persistence.
				createEntityManagerFactory("jpaHF");
	}
	
	/**
	 * Metodo di recupero dell'istanza Singleton.
	 * @return
	 */
	public static synchronized JpaUtils getInstance() {
		
		if(_instance == null) {
			_instance = new JpaUtils();
		}
		return _instance;
	}
	
	/**
	 * Metodo per il recupero dell'EntityManager.
	 * @return
	 */
	public EntityManager getEntityManager() {
		return emfactory.createEntityManager();
	}
}
