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
public class Test {

    public static void main(String[] args) {

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create"); // create (항상 새로 만든다 있으면 drop 후 만듬), update( 없으면 만들고 create, 있으면 변경되어야 하면 변경 alter)

        EntityManagerFactory emf = 
                new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                        new MyPersistenceUnitInfo(), props
                );
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

        	// #1 OneToOne Person Owner Entity person 만 persist : Passport null 인 상태로 insert
//            {
//            	Person person = new Person();
//            	person.setName("홍길동");
//            	
//            	Passport passport = new Passport();
//            	passport.setNumber("Kor123");
//            
//            	em.persist(person);
//            }
            
            // #2 OneToOne Person Owner Entity person, Passport 모두 persist : Person Passport null 인 상태로 insert, Passport insert
//            {
//            	Person person = new Person();
//            	person.setName("홍길동");
//            	
//            	Passport passport = new Passport();
//            	passport.setNumber("Kor123");
//            	
//            	em.persist(person);
//            	em.persist(passport);
//            }
            
            // #3 OneToOne 
            // Person, Passport 연결
            // Person Owner Entity person, Passport 모두 persist : Person Passport null 인 상태로 insert, Passport insert, Person 의 Passport update
//            {
//            	Person person = new Person();
//            	person.setName("홍길동");
//            	
//            	Passport passport = new Passport();
//            	passport.setNumber("Kor123");
//            	
//            	person.setPassport(passport);
//            	
//            	em.persist(person);
//            	em.persist(passport);
	            /*
	            Hibernate: insert into Person (name,passport) values (?,?)
	            Hibernate: insert into Passport (number) values (?)
	            Hibernate: update Person set name=?,passport=? where id=?
	            */
//            }

            
            // #3-2 OneToOne 
            // Person, Passport 연결
            // Person Owner Entity person, Passport 모두 persist : Person Passport 값을 가진 상태로 insert, Passport insert
//            {
//            	Person person = new Person();
//            	person.setName("홍길동");
//            	
//            	Passport passport = new Passport();
//            	passport.setNumber("Kor123");
//            	
//            	person.setPassport(passport);
//            	
//            	em.persist(passport);
//            	em.persist(person);
//                /*
//                Hibernate: insert into Passport (number) values (?)
//                Hibernate: insert into Person (name,passport) values (?,?)
//    			*/
//            }
            
            // #4 OneToOne 
            // Person, Passport 연결
            // Person Owner Entity person 만 persist : 오류
//            {
//            	Person person = new Person();
//            	person.setName("홍길동");
//            	
//            	Passport passport = new Passport();
//            	passport.setNumber("Kor123");
//            	
//            	person.setPassport(passport);
//            	
//            	em.persist(person);
//                /*
//org.hibernate.TransientPropertyValueException: 
//object references an unsaved transient instance - save the transient instance before flushing : com.mycom.myapp.entity.Person.passport -> com.mycom.myapp.entity.Passport
//    			*/
//            }
            
            // #5 OneToOne 
            // Person, Passport 연결
            // Person 에 CascadeType.PERSIST 연결
            // Person Owner Entity person 만 persist : Person Passport 값을 가진 상태로 insert, Passport insert
            {
            	Person person = new Person();
            	person.setName("홍길동");
            	
            	Passport passport = new Passport();
            	passport.setNumber("Kor123");
            	
            	person.setPassport(passport);
            	
            	em.persist(person);
            	
                /*
				Hibernate: insert into Passport (number) values (?)
				Hibernate: insert into Person (name,passport) values (?,?)
    			*/
            }

            
            em.getTransaction().commit(); // update 커밋할 때 수행
        } finally {
            em.close();
        }
    }
}
