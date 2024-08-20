package com.mycom.myapp;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mycom.myapp.config.MyPersistenceUnitInfo;
import com.mycom.myapp.entity.Passport;
import com.mycom.myapp.entity.Person;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// key 설정
public class Test2 {

    public static void main(String[] args) {

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
//      props.put("hibernate.hbm2ddl.auto", "create"); // create (항상 새로 만든다 있으면 drop 후 만듬), update( 없으면 만들고 create, 있으면 변경되어야 하면 변경 alter)

        EntityManagerFactory emf = 
                new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                        new MyPersistenceUnitInfo(), props
                );
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // #A Passport find()
//            {
//            	Passport passport = em.find(Passport.class, 1);
//            	System.out.println(passport);
//            	
//            	/*
//            	Hibernate: select p1_0.id,p1_0.number from Passport p1_0 where p1_0.id=?
//				Passport [id=1, number=Kor123]
//            	 */
//            }
            
            // #B Person find()
//            {
//            	Person person = em.find(Person.class, 1);
//            	System.out.println(person);
//            	
//            	/*
//            	Hibernate: select p1_0.id,p1_0.number from Passport p1_0 where p1_0.id=?
//				Passport [id=1, number=Kor123]
//            	 */
//            }
            
            // #C Person find() & Person FetchType.LAZY
            {
            	Person person = em.find(Person.class, 1);
            	System.out.println(person);
            	
            	/*
				Hibernate: select p1_0.id,p1_0.name,p1_0.passport from Person p1_0 where p1_0.id=?
				Person [id=1, name=홍길동]
            	 */
            	try {
            		Thread.sleep(10000);
            	}catch(Exception e) {
            		e.printStackTrace();
            	}        	
            	System.out.println(person.getPassport());
            	/*
				Hibernate: select p1_0.id,p1_0.number from Passport p1_0 where p1_0.id=?
				Passport [id=1, number=Kor123]
            	 */
            }           
            em.getTransaction().commit(); // update 커밋할 때 수행
        } finally {
            em.close();
        }
    }
}
