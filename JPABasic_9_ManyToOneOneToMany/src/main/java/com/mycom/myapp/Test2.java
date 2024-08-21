package com.mycom.myapp;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mycom.myapp.config.MyPersistenceUnitInfo;
import com.mycom.myapp.entity.Comment;
import com.mycom.myapp.entity.Post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

// key 설정
public class Test2 {

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
			
			// #A
			// Post find()
			// Post 만 가져오기
			// @OneToMany 의 FetchType 이 LAZY
//			{
//				Post post = em.find(Post.class, 1);
//				System.out.println(post);				
///*
//Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
//Post [id=1, title=Post 1, content=Post 1 Content]				
// */
//				try {
//					Thread.sleep(5000);
//				}catch(Exception e) {
//					e.printStackTrace();
//				}
//				
//				// Comment 가 필요할 때 다시 sql 수행
//				System.out.println(post.getComments());
///*
//Hibernate: select c1_0.post_id,c1_0.id,c1_0.content from Comment c1_0 where c1_0.post_id=?
//[Comment [id=1, content=Comment 1 Content], Comment [id=2, content=Comment 2 Content]]				
// */
//			}

			// #B
			// Comment find()
			// @ManyToOne 의 FetchType EAGER
			// join 수행, Comment 와 Post 함께 가져온다.
//			{
//				Comment c1 = em.find(Comment.class, 1);
//				System.out.println(c1);				
///*
//Hibernate: select c1_0.id,c1_0.content,p1_0.id,p1_0.content,p1_0.title from Comment c1_0 left join Post p1_0 on p1_0.id=c1_0.post_id where c1_0.id=?
//Comment [id=1, content=Comment 1 Content]				
// */
//				try {
//					Thread.sleep(5000);
//				}catch(Exception e) {
//					e.printStackTrace();
//				}
//				
//				// Post 사용할 때 다시 sql 수행 (X)
//				System.out.println(c1.getPost());
///*
//Post [id=1, title=Post 1, content=Post 1 Content]			
// */
//			}
			
			// #C
			// Post FetchType 을 EAGER 로 변경
			// Post find()
			// join 수행, 미리 Post 와 Comment 모두 가져온다.
//			{
//				Post post = em.find(Post.class, 1);
//				System.out.println(post);				
///*
//Hibernate: select p1_0.id,p1_0.content,p1_0.title,c1_0.post_id,c1_0.id,c1_0.content from Post p1_0 left join Comment c1_0 on p1_0.id=c1_0.post_id where p1_0.id=?
//Post [id=1, title=Post 1, content=Post 1 Content]				
// */
//				try {
//					Thread.sleep(5000);
//				}catch(Exception e) {
//					e.printStackTrace();
//				}
//				
//				// Comment 가 필요할 때 다시 sql 수행 (X)
//				System.out.println(post.getComments());
///*
//[Comment [id=1, content=Comment 1 Content], Comment [id=2, content=Comment 2 Content]]				
// */
//			}	
			
			// #D
			// Comment FetchType LAZY 로 변경
			// Comment find()
			// join 수행 X, Comment 만 가져온다.
			{
				Comment c1 = em.find(Comment.class, 1);
				System.out.println(c1);				
/*
Hibernate: select c1_0.id,c1_0.content,c1_0.post_id from Comment c1_0 where c1_0.id=?
Comment [id=1, content=Comment 1 Content]			
 */
				try {
					Thread.sleep(5000);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				// Post 사용할 때 다시 sql 수행
				System.out.println(c1.getPost());
/*
Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
Post [id=1, title=Post 1, content=Post 1 Content]		
 */
			}			
			em.getTransaction().commit();
		}finally {
			em.close();
		}
	}

}