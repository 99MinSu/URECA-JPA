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
public class Test {

	public static void main(String[] args) {
		
		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.hbm2ddl.auto", "create"); // create (항상 새로 만든다), update ( 없으면 만들고, 있으면 변경되어야 하면 변경 )

		EntityManagerFactory emf = 
				new HibernatePersistenceProvider().createContainerEntityManagerFactory(
						new MyPersistenceUnitInfo(), props
				);
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			// #1 테이블 생성 확인
//			{
//				
///*
//Hibernate: create table Comment (id integer not null auto_increment, content varchar(255), primary key (id)) engine=InnoDB
//Hibernate: create table Post (id integer not null auto_increment, content varchar(255), title varchar(255), primary key (id)) engine=InnoDB
//Hibernate: create table Post_Comment (Post_id integer not null, comments_id integer not null) engine=InnoDB
//Hibernate: alter table Post_Comment add constraint UK5nd2ucnctmvwq31ovab2vi00x unique (comments_id)
//Hibernate: alter table Post_Comment add constraint FK1yqm25y6mj9vmmxj4ev7aj2q1 foreign key (comments_id) references Comment (id)
//Hibernate: alter table Post_Comment add constraint FKgtvmchqpjf5ysbhyg52cofvrn foreign key (Post_id) references Post (id)		
// */
//			}
			
			// #2
			// Post Entity 1 개 생성, persist
			// Post 1건 insert OK
//			{
//				Post p = new Post();
//				p.setTitle("Post 1");
//				p.setContent("Post 1 Content");
//				
//				em.persist(p);
//				
///*
//Hibernate: insert into Post (content,title) values (?,?)				
// */
//			}
			
			// #3
			// Comment Entity 2 개 생성, persist 
			// Comment 2 건 insert OK (but, 올바르지 않다. Post_Comment 에 이 Comment 들이 어떤 Post의 Comment 들인지 데이터가 추가로 필요한 상황)
//			{
//				Comment c1 = new Comment();
//				c1.setContent("comment 1 Content");
//				
//				Comment c2 = new Comment();
//				c2.setContent("comment 2 Content");
//				
//				em.persist(c1);
//				em.persist(c2);
//				
///*
//Hibernate: insert into Comment (content) values (?)
//Hibernate: insert into Comment (content) values (?)				
// */
//			}		
			
			// #4
			// Post 1, Comment 2 Entity 생성
			// Post - Comment 연결 (O)
			// Post, Comment 모두 persist
			// 
//			{
//				Post p = new Post();
//				p.setTitle("Post 1");
//				p.setContent("Post 1 Content");
//				
//				Comment c1 = new Comment();
//				c1.setContent("comment 1 Content");
//				
//				Comment c2 = new Comment();
//				c2.setContent("comment 2 Content");
//
//				// 연결
//				p.setComments(List.of(c1, c2));
//				
//				em.persist(p);				
//				em.persist(c1);
//				em.persist(c2);
//				
///*
//Hibernate: insert into Post (content,title) values (?,?)
//Hibernate: insert into Comment (content) values (?)
//Hibernate: insert into Comment (content) values (?)
//Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)
//Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)		
// */
//			}
			
			// #5
			// Post 1, Comment 2 Entity 생성
			// Post - Comment 연결 (O)
			// Post 만 persist
			// 오류 발생
			// org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: com.mycom.myapp.entity.Comment
//			{
//				Post p = new Post();
//				p.setTitle("Post 1");
//				p.setContent("Post 1 Content");
//				
//				Comment c1 = new Comment();
//				c1.setContent("comment 1 Content");
//				
//				Comment c2 = new Comment();
//				c2.setContent("comment 2 Content");
//
//				// 연결
//				p.setComments(List.of(c1, c2));
//				
//				em.persist(p);	// Post 만 persist			
////				em.persist(c1);
////				em.persist(c2);
//			}	
			
			// #6
			// Post 1, Comment 2 Entity 생성
			// Post - Comment 연결 (O)
			// Post cascadeType PERSIST 로 설정
			// Post 만 persist
			// 오류 발생
			// org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: com.mycom.myapp.entity.Comment
			{
				Post p = new Post();
				p.setTitle("Post 1");
				p.setContent("Post 1 Content");
				
				Comment c1 = new Comment();
				c1.setContent("comment 1 Content");
				
				Comment c2 = new Comment();
				c2.setContent("comment 2 Content");

				// 연결
				p.setComments(List.of(c1, c2));
				
				em.persist(p);	// Post 만 persist			
//				em.persist(c1);
//				em.persist(c2);
				
/*
Hibernate: insert into Post (content,title) values (?,?)
Hibernate: insert into Comment (content) values (?)
Hibernate: insert into Comment (content) values (?)
Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)
Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)				
 */
			}
			
			em.getTransaction().commit();
		}finally {
			em.close();
		}
	}

}