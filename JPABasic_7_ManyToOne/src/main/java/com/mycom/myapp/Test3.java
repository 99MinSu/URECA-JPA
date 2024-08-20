package com.mycom.myapp;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mycom.myapp.config.MyPersistenceUnitInfo;
import com.mycom.myapp.entity.Comment;
import com.mycom.myapp.entity.Post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// OneToOne bi-directional
public class Test3 {

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
            
            // 기존 Post 에 새 Comment 추가
            {
            	// 기존 Post <- find()
            	// 새 Comment <- Entity 객체 생성
            	// 새 Comment Entity 에 기존 Post Entity 연결
            	// 새 Comment Entity 를 persist
            	Post p = em.find(Post.class, 1);
            	
            	Comment c3 = new Comment();
            	c3.setContent("Comment 3 Content");
            	
            	c3.setPost(p);
            	
            	em.persist(c3);
            }
            em.getTransaction().commit(); // update 커밋할 때 수행
        } finally {
            em.close();
        }
    }
}
