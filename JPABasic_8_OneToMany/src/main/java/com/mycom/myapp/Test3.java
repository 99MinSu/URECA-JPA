package com.mycom.myapp;

import java.util.HashMap;
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
				//   Post 의 Comment List 를 가져와서 add ( 새 Comment )
				// 새 Comment Entity 를 persist
				// @OneToMany 에서 Post 에 새로운 Comment 의 등록은 기존 Post Entity의 Comment 를 삭제, 등록 과정으로 처리 
				Post p = em.find(Post.class, 1);

				Comment c3 = new Comment();
				c3.setContent("comment 3 Content");

				// 연결
				System.out.println(p.getComments().size());
				p.getComments().add(c3);
						
				em.persist(c3);
				
/*
Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
Hibernate: insert into Comment (content) values (?)
Hibernate: delete from Post_Comment where Post_id=?
Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)
 */
			}
			em.getTransaction().commit();
		}finally {
			em.close();
		}
	}

}