package com.mycom.myapp;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mycom.myapp.config.MyPersistenceUnitInfo;
import com.mycom.myapp.entity.Comment;
import com.mycom.myapp.entity.Post;

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
            // table 생성
            // 2개의 테이블 Post, Comment 테이블이 만들어진다. Comment 테이블에 post_id FK 컬럼이 만들어진다.
            {
            	
            }
            
            // #2
            // Post Entity 만 1개 persist
            // Post insert 1건 정상
//            {
//            	Post p = new Post();
//            	p.setTitle("Post 1");
//            	p.setContent("Post 1 Content");
//            	
//            	em.persist(p);
//            }
            
            // #3
            // Comment Entity 만 2개 persist
            // Comment insert 2건 정상 ( 단, 연관관계 column 인 post_id 는 모두 null )
//            {
//            	Comment c1 = new Comment();
//            	c1.setContent("Comment 1 Content");
//            	
//            	Comment c2 = new Comment();
//            	c2.setContent("Comment 2 Content");
//            	
//            	em.persist(c1);
//            	em.persist(c2);
//            }
            
            // #4
            // Post 1, Comment 2 생성
            // Post, Comment 모두 연결
            // Post 1개만 persist
            // Post insert 1건 정상 ( 연결된 연관관계에서 Non Owner 관계이므로)
//            {    	
//            	Post p = new Post();
//            	p.setTitle("Post 1");
//            	p.setContent("Post 1 Content");
//            	
//            	Comment c1 = new Comment();
//            	c1.setContent("Comment 1 Content");
//            	
//            	Comment c2 = new Comment();
//            	c2.setContent("Comment 2 Content");
//            	
//            	c1.setPost(p);
//            	c2.setPost(p);
//            	
//            	// Post 만 persist
//            	em.persist(p);
//            }
            
            // #5
            // Post 1, Comment 2 생성
            // Post, Comment 모두 연결
            // Comment 2개만 persist
            // 오류 발생
//            {    	
//            	Post p = new Post();
//            	p.setTitle("Post 1");
//            	p.setContent("Post 1 Content");
//            	
//            	Comment c1 = new Comment();
//            	c1.setContent("Comment 1 Content");
//            	
//            	Comment c2 = new Comment();
//            	c2.setContent("Comment 2 Content");
//            	
//            	c1.setPost(p);
//            	c2.setPost(p);
//            	
//            	em.persist(c1);
//            	em.persist(c2);
//            }
            
            // #6
            // Post 1, Comment 2 생성
            // Post, Comment 모두 연결
            // Comment 2개, Post 1개 모두 persist
            // Comment 2 건, Post 1 건 insert 후 Comment 2 건 update
//            {    	
//            	Post p = new Post();
//            	p.setTitle("Post 1");
//            	p.setContent("Post 1 Content");
//            	
//            	Comment c1 = new Comment();
//            	c1.setContent("Comment 1 Content");
//            	
//            	Comment c2 = new Comment();
//            	c2.setContent("Comment 2 Content");
//            	
//            	// 연결
//            	c1.setPost(p);
//            	c2.setPost(p);
//            	
//            	em.persist(c1);
//            	em.persist(c2);
//            	em.persist(p); // Post 나중에
//            }
            
            // #7
            // Post 1, Comment 2 생성
            // Post, Comment 모두 연결
            // Comment 2개, Post 1개 모두 persist
            // Post 1 건 , Comment 2 건, insert 후 Comment 2 건 update
            // FK 를 제공하는 Entity 먼저 persist 하는 게 성능 면에서 유리
//            {    	
//            	Post p = new Post();
//            	p.setTitle("Post 1");
//            	p.setContent("Post 1 Content");
//            	
//            	Comment c1 = new Comment();
//            	c1.setContent("Comment 1 Content");
//            	
//            	Comment c2 = new Comment();
//            	c2.setContent("Comment 2 Content");
//            	
//            	// 연결
//            	c1.setPost(p);
//            	c2.setPost(p);
//            	
//            	em.persist(p); // Post 먼저
//            	em.persist(c1);
//            	em.persist(c2);
//            }
            
            // #8
            // Post 1, Comment 2 생성
            // Post, Comment 모두 연결
            // Comment 2개, Post 1개 모두 persist
            // Comment 에 CasCadeType PERSIST
            // Post 1 건 , Comment 2 건, insert 후 Comment 2 건 update
            // FK 를 제공하는 Entity 먼저 persist 하는 게 성능 면에서 유리
            {    	
            	Post p = new Post();
            	p.setTitle("Post 1");
            	p.setContent("Post 1 Content");
            	
            	Comment c1 = new Comment();
            	c1.setContent("Comment 1 Content");
            	
            	Comment c2 = new Comment();
            	c2.setContent("Comment 2 Content");
            	
            	// 연결
            	c1.setPost(p);
            	c2.setPost(p);
            	
            	em.persist(c1);
            	em.persist(c2);
            }
            em.getTransaction().commit(); // update 커밋할 때 수행
        } finally {
            em.close();
        }
    }
}
