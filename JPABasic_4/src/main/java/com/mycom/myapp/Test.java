package com.mycom.myapp;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mycom.myapp.config.MyPersistenceUnitInfo;
import com.mycom.myapp.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// org.hibernate.UnknownEntityTypeException: Unable to locate entity descriptor: com.mycom.myapp.entity.Employee
// HashMap에 몇 가지 설정
public class Test {

    public static void main(String[] args) {

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "update"); // create (항상 새로 만든다 있으면 drop 후 만듬), update( 없으면 만들고 create, 있으면 변경되어야 하면 변경 alter)
        // hbm2ddl <= Hibernate Mapping to(2) DDL
        EntityManagerFactory emf = 
                new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                        new MyPersistenceUnitInfo(), props
                );
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

//            // sql_확인
//            {
//                Employee e1 = new Employee();
//                e1.setId(1);
//                e1.setName("이민수");
//                e1.setAddress("익산 어디");
//                em.persist(e1);
//            }
//            {
//                Employee e1 = em.find(Employee.class, 1);
//                System.out.println(e1);
//            }
//
//            {
//                Employee e1 = new Employee();
//                e1.setId(1);
//                e1.setName("이민수");
//                e1.setAddress("익산 어디");
//                em.persist(e1);
//
//                System.out.println(e1);
//                // delay
//                try {
//                    Thread.sleep(10000);
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//            }

//            {
//                Employee e1 = em.find(Employee.class, 1);
//                e1.setAddress("제주 어디");
//                System.out.println(e1);
//
//                try {
//                    Thread.sleep(10000);
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//            }
            
            // create, update 설정으로 현재 employee table empty
//            {
//            	Employee e1 = em.find(Employee.class, 1);
//            	System.out.println(e1);
//            	
//            	e1 = new Employee();
//                e1.setId(1);
//                e1.setName("이민수");
//                e1.setAddress("익산 어디");
//                em.persist(e1);
//                
//                e1 = em.find(Employee.class, 1); // select 수행 X
//                // find() 는 persistence context 에 있으면 거기서 찾는다. 없으면 DB에서 가져온다.
//                System.out.println(e1);
//            }
            // getReference()
            // 현재 DB 1 건 employee 데이터 존재
//            {
//            	// find() 즉각 실행
//            	// getReference 사용될 때 실행
////            	Employee e1 = em.find(Employee.class, 1); // 즉시
//            	Employee e1 = em.getReference(Employee.class, 1); // find() X
////            	e1.setName("이길동");
//            	
//                try {
//                    Thread.sleep(10000);
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//            	
//            	System.out.println(e1);
//            }
            
            // remove
            // persistance context 에서 제거          
//            {
//
//            	Employee e1 = em.find(Employee.class, 1); 
//        	
//            	em.remove(e1);
//                try {
//                    Thread.sleep(10000);
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//            	
//            	System.out.println(e1);
//            }
//            
            
            // refresh       
            {

            	Employee e1 = em.find(Employee.class, 1); 
        	
            	e1.setAddress("아까 거기");
            	
            	System.out.println(e1);
            	
                try {
                    Thread.sleep(10000);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                
                em.refresh(e1); // select 수행으로 객체 <- table
                
            	System.out.println(e1);
            }

            em.getTransaction().commit(); // update 커밋할 때 수행
        } finally {
            em.close();
        }
    }
}
