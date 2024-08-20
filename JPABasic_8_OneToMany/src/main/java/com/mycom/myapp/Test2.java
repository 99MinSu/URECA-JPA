package com.mycom.myapp;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mycom.myapp.config.MyPersistenceUnitInfo;
import com.mycom.myapp.entity.Post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

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
			// Post 1건 find()
			// join (X) Post 1건 select
			// @OneToMany FethchType 이 LAZY
//			{
//				Post post = em.find(Post.class, 1);
//				System.out.println(post);
//				try {
//					Thread.sleep(5000);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				// Post Entity의 연관관계의 comments 를 사용하는 시점에 다시 select (join) 수행
//				System.out.println(post.getComments());
//			}
			
			// #B
			// Post 1건 find()
			// join (X) Post 1건 select
			// @OneToMany FethchType 이 LAZY
//			{
//				Comment c = em.find(Comment.class, 1);
//				System.out.println(c);
//			}
			
			
			
			// #C
			// Post 1건 find()
			// @OneToMany FethchType 이 EAGER
			// join 수행 ,미리 post + comment 함게 가지고 온다.
			{
				Post post = em.find(Post.class, 1);
				System.out.println(post);
				
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// Post Entity의 연관관계의 comments 를 사용하는 시점에 다시 select 수행 X
				System.out.println(post.getComments());
			}
			
			em.getTransaction().commit();
		}finally {
			em.close();
		}
	}

}