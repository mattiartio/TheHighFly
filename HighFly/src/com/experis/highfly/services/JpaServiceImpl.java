package com.experis.highfly.services;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.experis.highfly.utils.DatabaseConfigBean;


//Service perchè è una classe di servizio ( ma forse andrà cambiato )
//Lo scope è singleton perchè ci serve una istanza singola dato che questo contiene la fabbrica degli EntityManager
//e di fabbriche di EntityManager ce ne deve essere una sola
@Service("jpaService")
@Scope(value = "singleton")
public class JpaServiceImpl implements JpaService {

	@Autowired(required=true)
	private DatabaseConfigBean configBean;
	
	private EntityManagerFactory emFactory = null;
	
	/*	
	    Crea l'EntityManagerFactory usando il file persistence.xml ( quello che utilizza JPA per mappare i beans )
		e per dirgli quale file persistence.xml utilizzare gli diamo il nome della persistence-unit che è
		all'interno del configBean grazie all'application-context.xml e al mapping che gli abbiamo fatto
	*/
	@PostConstruct
	public void init() {
		emFactory = Persistence.createEntityManagerFactory(configBean.getPersistenceUnitName());
	}

	@Override
	public EntityManager getDatabaseConnection(){
		return emFactory.createEntityManager();
	}

	@PreDestroy
	@Override
	public void terminateService(){
		emFactory.close();
		
	}

}
