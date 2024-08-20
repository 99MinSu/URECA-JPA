package com.mycom.myapp;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mycom.myapp.config.MyPersistenceUnitInfo;
import com.mycom.myapp.entity.Comment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// OneToOne bi-directional
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
            
            // #A
            // Post 1 건 find()
//            {
//            	Post p = em.find(Post.class, 1);
//            	System.out.println(p);
//            }
            
            // #B
            // Comment 1 건 find()
            // join 으로 연관된 Post 함께 select
            // @ManyToOne 의 FetchType 이 EAGER
//            {
//            	Comment c1 = em.find(Comment.class, 1);
//            	System.out.println(c1);
//            	
//            	try {
//            		Thread.sleep(5000);
//            	}catch(Exception e) {
//            		e.printStackTrace();
//            	}
//            	
//            	// Comment 와 연관된 Post 를 가져올 때 다시 sql 수행 X
//            	System.out.println(c1.getPost());
//            }
            
            // #C
            // Comment 1 건 find()
            // Comment @ManyToOne 의 FetchType 이 LAZY 로 변경
            // Comment 만 가져온다 join (X)
            {
            	Comment c1 = em.find(Comment.class, 1);
            	System.out.println(c1);
            	
            	try {
            		Thread.sleep(5000);
            	}catch(Exception e) {
            		e.printStackTrace();
            	}
            	
            	// Comment 와 연관된 Post 를 가져올 때 다시 sql 수행 
            	System.out.println(c1.getPost());
            }
            em.getTransaction().commit(); // update 커밋할 때 수행
        } finally {
            em.close();
        }
    }
}
