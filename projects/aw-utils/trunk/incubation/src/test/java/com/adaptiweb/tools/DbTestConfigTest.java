package com.adaptiweb.tools;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.adaptiweb.tools.dbtest.DbTestConfig;
import com.adaptiweb.tools.dbtest.DbTestConnection;
import com.adaptiweb.tools.dbtest.DbTestExecution;
import com.adaptiweb.tools.dbtest.DbTestJNDIDataSource;
import com.adaptiweb.tools.entity.Person;


@DbTestJNDIDataSource(jndiName="java:comp/env/jdbc/DerbyDS",
	url="jdbc:derby:target/derby-db;create=true",
	driver=org.apache.derby.jdbc.EmbeddedDriver.class)
@DbTestConnection(persistenceUnit="test-unit")
public class DbTestConfigTest {

	@Test
	public void testCreateObject() {
		DbTestConfig.create(this).execute();
	}

	@DbTestExecution(order=1)
	public void initDatabase(EntityManager em) {
		Person entity = new Person();
		entity.setName("Eddie Vedder");
		em.persist(entity);
	}
	
	@DbTestExecution(order=2, useTransaction=false)
	public void notInitDatabase(EntityManager em) {
		Person entity = new Person();
		entity.setName("Karel Kryl");
		em.persist(entity);
	}
	
	@DbTestExecution(order=4, useTransaction=false)
	public void selectFromDatabase(EntityManager em) {
		List<?> list = em.createQuery("from Person").getResultList();
		assertEquals(1, list.size());
		System.out.println(list);
		assertEquals("Eddie Vedder", ((Person) list.get(0)).getName());
	}

}
