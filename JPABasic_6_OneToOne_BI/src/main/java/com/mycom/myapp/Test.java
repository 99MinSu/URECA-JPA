package com.mycom.myapp;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mycom.myapp.config.MyPersistenceUnitInfo;
import com.mycom.myapp.entity.Passport;
import com.mycom.myapp.entity.Person;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// OneToOne bi-directional 관계는 Owner Entity 한 쪽에만 있을 때 해당 column 이 만들어 진다.
// 동등한 Owner Entity 인 경우는 양쪽에 다 만들어 진다.
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
            
            // #1
            // Owner Entity Person 에 cascade 속성 X
            // 두 Entity 모두 insert
            {
            	Person person = new Person();
            	person.setName("홍길동");
            	
            	Passport passport = new Passport();
            	passport.setNumber("kor123");
            	
            	person.setPassport(passport);
            	passport.setPerson(person);
            	
            	em.persist(passport);
            	em.persist(person);
            }
            
            // #2
            // Owner Entity Person 에 cascade 속성 O
//            {
//            	Person person = new Person();
//            	person.setName("홍길동");
//            	
//            	Passport passport = new Passport();
//            	passport.setNumber("kor123");
//            	
//            	person.setPassport(passport);
////            	passport.setPerson(person);
//            	
////            	em.persist(passport);
//            	em.persist(person);
//            }
            
            // #3
            // Passport 에 cascade 속성 O
//            {
//            	Person person = new Person();
//            	person.setName("홍길동");
//            	
//            	Passport passport = new Passport();
//            	passport.setNumber("kor123");
//            	
////            	person.setPassport(passport);
//            	passport.setPerson(person);
//            	
//            	em.persist(passport);
////            	em.persist(person);
//            }
            em.getTransaction().commit(); // update 커밋할 때 수행
        } finally {
            em.close();
        }
    }
}
