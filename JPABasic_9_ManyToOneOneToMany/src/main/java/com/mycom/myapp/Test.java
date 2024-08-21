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
			
			// #1 
			// 테이블 생성 확인
			{
/*
Hibernate: create table Comment (id integer not null auto_increment, post_id integer, content varchar(255), primary key (id)) engine=InnoDB
Hibernate: create table Post (id integer not null auto_increment, content varchar(255), title varchar(255), primary key (id)) engine=InnoDB
Hibernate: alter table Comment add constraint FKqb0rnht649ifuh6gev5lwvx8x foreign key (post_id) references Post (id)
 */
			}
			
			// #2 
			// Post Entity persist
			// Post insert 1 건 OK
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
			// Comment Entity 2 persist
			// Comment insert 2 건 OK <= 정상적인 데이터  X
			// 만약 DB comment table 의 post_id 컬럼이 not null 인 경우 오류 발생
			//  java.sql.SQLIntegrityConstraintViolationException: Column 'post_id' cannot be null
//			{
//				Comment c1 = new Comment();
//				c1.setContent("Comment 1 Content");
//				
//				Comment c2 = new Comment();
//				c2.setContent("Comment 2 Content");
//				
//				em.persist(c1);
//				em.persist(c2);
//				
///*
//Hibernate: insert into Comment (content,post_id) values (?,?)
//Hibernate: insert into Comment (content,post_id) values (?,?)				
// */
//			}
			
			// #4 
			// Post 1, Comment 2 Entity 생성
			// Post, Comment 연결 (Post 중심)
			// Post 만 persist
			// Post 1 건만 insert
			// @OneToMany uni 실습에서는 오류 (Post 가 Owner Entity)
			// @OneToMany 와 @ManyToOne 의 bi 관계이고 @OneToMany 가 Non Owner Entity 이므로.
//			{
//				Post p = new Post();
//				p.setTitle("Post 1");
//				p.setContent("Post 1 Content");
//				
//				Comment c1 = new Comment();
//				c1.setContent("Comment 1 Content");
//				
//				Comment c2 = new Comment();
//				c2.setContent("Comment 2 Content");
//				
//				// Post 중심 연결
//				p.setComments(List.of(c1, c2));
//				
//				// Post 만 persist
//				em.persist(p);
//				
///*
//Hibernate: insert into Comment (content,post_id) values (?,?)
//Hibernate: insert into Comment (content,post_id) values (?,?)				
// */
//			}
			
			// #5 
			// Post 1, Comment 2 Entity 생성
			// Post, Comment 연결 (Comment 중심)
			// Comment 만 persist
			// 오류 발생
			// org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing : com.mycom.myapp.entity.Comment.post -> com.mycom.myapp.entity.Post
//			{
//				Post p = new Post();
//				p.setTitle("Post 1");
//				p.setContent("Post 1 Content");
//				
//				Comment c1 = new Comment();
//				c1.setContent("Comment 1 Content");
//				
//				Comment c2 = new Comment();
//				c2.setContent("Comment 2 Content");
//				
//				// Comment 중심 연결
//				c1.setPost(p);
//				c2.setPost(p);
//				
//				// Comment 만 persist
//				em.persist(c1);
//				em.persist(c2);
//				
//			}
			
			// #6 
			// Post 1, Comment 2 Entity 생성
			// Post, Comment 둘다 모두 연결
			// Post, Comment 모두 persist ( Comment -> Post 순 )
			// 
//			{
//				Post p = new Post();
//				p.setTitle("Post 1");
//				p.setContent("Post 1 Content");
//				
//				Comment c1 = new Comment();
//				c1.setContent("Comment 1 Content");
//				
//				Comment c2 = new Comment();
//				c2.setContent("Comment 2 Content");
//				
//				
//				// Post, Comment 모두 연결
//				p.setComments(List.of(c1, c2));
//				c1.setPost(p);
//				c2.setPost(p);
//				
//				// Post, Comment 모두 persist
//				em.persist(c1);
//				em.persist(c2);
//				em.persist(p); // 뒤에
///*
// 만약 post_id 컬럼이 not null 이면 오류 발생
//Hibernate: insert into Comment (content,post_id) values (?,?)
//Hibernate: insert into Comment (content,post_id) values (?,?)
//Hibernate: insert into Post (content,title) values (?,?)
//Hibernate: update Comment set content=?,post_id=? where id=?
//Hibernate: update Comment set content=?,post_id=? where id=?			
// */
//			}
			
			// #7 
			// Post 1, Comment 2 Entity 생성
			// Post, Comment 둘다 모두 연결
			// Post, Comment 모두 persist  ( Post -> Comment 순 )
			// #6 에 비해 더 나은 방법
//			{
//				Post p = new Post();
//				p.setTitle("Post 1");
//				p.setContent("Post 1 Content");
//				
//				Comment c1 = new Comment();
//				c1.setContent("Comment 1 Content");
//				
//				Comment c2 = new Comment();
//				c2.setContent("Comment 2 Content");
//				
//				
//				// Post, Comment 모두 연결
//				p.setComments(List.of(c1, c2));
//				c1.setPost(p);
//				c2.setPost(p);
//				
//				// Post, Comment 모두 persist
//				em.persist(p); // 먼저
//				em.persist(c1);
//				em.persist(c2);
//				
///*
//Hibernate: insert into Post (content,title) values (?,?)
//Hibernate: insert into Comment (content,post_id) values (?,?)
//Hibernate: insert into Comment (content,post_id) values (?,?)			
// */
//			}
			
			
			// #8
			// Post 1, Comment 2 Entity 생성
			// Comment 중심 연결
			// Post, Comment 모두 persist  ( Post -> Comment 순 )
			// #7 의 코드 중 persist 관련 Post 중심의 연결 코드는 필요 X
//			{
//				Post p = new Post();
//				p.setTitle("Post 1");
//				p.setContent("Post 1 Content");
//				
//				Comment c1 = new Comment();
//				c1.setContent("Comment 1 Content");
//				
//				Comment c2 = new Comment();
//				c2.setContent("Comment 2 Content");
//				
//				
//				// Comment 모두 연결
////				p.setComments(List.of(c1, c2)); // 불필요한 코드
//				c1.setPost(p);
//				c2.setPost(p);
//				
//				// Post, Comment 모두 persist
//				em.persist(p); // 먼저
//				em.persist(c1);
//				em.persist(c2);
//				
///*
//Hibernate: insert into Post (content,title) values (?,?)
//Hibernate: insert into Comment (content,post_id) values (?,?)
//Hibernate: insert into Comment (content,post_id) values (?,?)		
// */
//			}	
			
			// # 9
			// Comment 에 cascadeType PERSIST
			// Post 1, Comment 2 Entity 생성
			// Post, Comment 연결 (Comment 중심)
			// Comment 만 persist
			// 3 건 insert OK
			{
				Post p = new Post();
				p.setTitle("Post 1");
				p.setContent("Post 1 Content");
				
				Comment c1 = new Comment();
				c1.setContent("Comment 1 Content");
				
				Comment c2 = new Comment();
				c2.setContent("Comment 2 Content");
				
				// Comment 중심 연결
				c1.setPost(p);
				c2.setPost(p);
				
				// Comment 만 persist
				em.persist(c1);
				em.persist(c2);
				
/*
Hibernate: insert into Post (content,title) values (?,?)
Hibernate: insert into Comment (content,post_id) values (?,?)
Hibernate: insert into Comment (content,post_id) values (?,?)				
 */
			}	
			
			// # 10
			// Post 에 cascadeType PERSIST 추가 mappedBy="post" 제거, Comment 는 원복
			// Post 1, Comment 2 Entity 생성
			// Post, Comment 연결 (Post 중심)
			// Post 만 persist
			// Post 가 Owner Entity 가 되고,  @OneToMany 처럼 join column  이 아닌 join table 을 별도로 구성하게 된다. 주의!!! 
//			{
//				Post p = new Post();
//				p.setTitle("Post 1");
//				p.setContent("Post 1 Content");
//				
//				Comment c1 = new Comment();
//				c1.setContent("Comment 1 Content");
//				
//				Comment c2 = new Comment();
//				c2.setContent("Comment 2 Content");
//				
//				// Post 중심 연결
//				p.setComments(List.of(c1, c2));
//				
//				// Post 만 persist
//				em.persist(p);
//				
///*
//Hibernate: insert into Post (content,title) values (?,?)
//Hibernate: insert into Comment (content,post_id) values (?,?)
//Hibernate: insert into Comment (content,post_id) values (?,?)				
// */
//			}
			
			em.getTransaction().commit();
		}finally {
			em.close();
		}
	}

}