package com.mycom.myapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mycom.myapp.config.MyPersistenceUnitInfo;
import com.mycom.myapp.entity.Comment;
import com.mycom.myapp.entity.Post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// key 설정
public class Test3 {

	public static void main(String[] args) {
		
		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
//		props.put("hibernate.hbm2ddl.auto", "create"); // create (항상 새로 만든다), update ( 없으면 만들고, 있으면 변경되어야 하면 변경 )

		EntityManagerFactory emf = 
				new HibernatePersistenceProvider().createContainerEntityManagerFactory(
						new MyPersistenceUnitInfo(), props
				);
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			// #가
			// 기존 Post 에 새 Comment 추가
			{
				// 기존 Post <- find()
				// 새 Comment <- Entity 객체 생성
				// Post Entity 에 새 Comment Entity 를 연결
				//   Comment 중심 setPost() 이용
				// 새 Comment Entity 를 persist
				
				Post p = em.find(Post.class, 1);

				Comment c3 = new Comment();
				c3.setContent("comment 3 Content");

				// 연결
				c3.setPost(p);
				// Comment persist
				em.persist(c3);
				
/*
Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
Hibernate: insert into Comment (content,post_id) values (?,?)
 */
			}
			em.getTransaction().commit();
		}finally {
			em.close();
		}
	}

}