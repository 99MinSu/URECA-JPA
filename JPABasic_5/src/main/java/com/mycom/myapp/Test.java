package com.mycom.myapp;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mycom.myapp.config.MyPersistenceUnitInfo;
import com.mycom.myapp.entity.Order;
import com.mycom.myapp.entity.key.OrderKey;

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

            // Key 생성 옵션 테스트
//            {
//            	Employee emp = new Employee();
//            	// AUTO, IDENTITY key 를 명시적으로 설정하면 오류 발생
//            	//org.hibernate.PersistentObjectException: detached entity passed to persist: com.mycom.myapp.entity.Employee
////            	emp.setId(1);
//            	emp.setName("이길동");
//            	emp.setAddress("경기 어디");
//            	
//            	em.persist(emp);
//            }
            
            // 복합키 생성 @IdClass
//            {
//            	// persist
//            	Product p = new Product();
//            	p.setCode("uplus");
//            	p.setNumber(1L);
//            	p.setColor("blue");
//            	
//            	em.persist(p);
//            	
//            	// find
//            	ProductKey key = new ProductKey();
////            	key.setCode("uplus2");
//            	key.setCode("uplus");
//            	key.setNumber(1L);
//            	
//            	Product p2 = em.find(Product.class, key); // key 연결
//            	System.out.println(p2);
//            }
            
//            // @EmbeddedID
//            {
////            	// persist
////            	// StudentKey 먼저 생성
////            	StudentKey key = new StudentKey();
////            	key.setCode("uplus");
////            	key.setNumber(1);
////            	
////            	Student s = new Student();
////            	s.setId(key);
////            	s.setName("홍길동");
////            	
////            	em.persist(s);
//            	
//            	// persist
//            	// StudentKey 먼저 생성
//            	StudentKey key = new StudentKey();
//            	key.setCode("uplus");
//            	key.setNumber(1);
//            	
//            	Student s2 = em.find(Student.class, key);
//            	System.out.println(s2);
//            }
            
            {
            	// persist
            	Order o = new Order();
            	o.setProductId(1);
            	o.setCustomerId(1);
            	o.setOrderCnt(3);
            	
            	em.persist(o);
            	
            	OrderKey key = new OrderKey();
            	key.setProductId(1);
            	key.setCustomerId(1);
            	
            	Order o2 = em.find(Order.class, key);
            	System.out.println(o2);
            }
            
            em.getTransaction().commit(); // update 커밋할 때 수행
        } finally {
            em.close();
        }
    }
}
