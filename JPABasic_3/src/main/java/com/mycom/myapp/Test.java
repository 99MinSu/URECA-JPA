package com.mycom.myapp;

import java.util.HashMap;


import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mycom.myapp.config.MyPersistenceUnitInfo;
import com.mycom.myapp.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// org.hibernate.UnknownEntityTypeException: Unable to locate entity descriptor: com.mycom.myapp.entity.Employee
public class Test {

   public static void main(String[] args) {

	  EntityManagerFactory emf = 
			 new HibernatePersistenceProvider().createContainerEntityManagerFactory(
					 new MyPersistenceUnitInfo(), new HashMap<>()
			 );
      EntityManager em = emf.createEntityManager();

      try {
         em.getTransaction().begin();
         
         // persist() => Entity 객체가 persistence context 안으로 들어온다.        
         // find() => DB 의 특정 row 를 persistence context 안으로 들어 오게 한다. Entity 객체로 표현
//         {
//             Employee e1 = em.find(Employee.class, 1);
//             System.out.println(e1);
//         }
         
//         {
//             Employee e1 = em.find(Employee.class, 1);
//             e1.setAddress("부산 어디"); // <- 이 시점에 DB 에 update 수행 X
//             System.out.println(e1);
//         }
         
//         {
//        	 // 여기서만 바뀌고 DB에서는 반영되지 않는다
//        	 // persist find 가 이니기 때문에
//             Employee e1 = new Employee();
//             e1.setId(1);
//             e1.setAddress("광주 어디"); // <- 이 시점에 DB 에 update 수행 X
//             System.out.println(e1);
//         }
         
//         {   // 업데이트가 1번 일어난 것이다.
//             Employee e1 = em.find(Employee.class, 1);
//             e1.setAddress("광주 어디"); 
//             e1.setAddress("부산 어디"); 
//             System.out.println(e1);
//         }
         
//         {  
//             Employee e1 = new Employee();
//             e1.setId(2);
//             e1.setAddress("전주 어디"); 
//             System.out.println(e1);
//             
////             em.persist(e1); //Duplicate entry '2' for key 'employee.PRIMARY' 
//             em.merge(e1); // 있으면 update 수행
//         }
         
         {  
             Employee e1 = new Employee();
             e1.setId(3);
             e1.setAddress("군산 어디"); 
             System.out.println(e1);
             
//             em.persist(e1); // insert 
             em.merge(e1); // 없으면 insert 수행
         }
          
         em.getTransaction().commit(); // update 커밋할 때 수행
      }finally {
         em.close();
      }
   }

}
