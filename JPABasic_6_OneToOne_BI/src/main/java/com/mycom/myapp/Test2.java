package com.mycom.myapp;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mycom.myapp.config.MyPersistenceUnitInfo;
import com.mycom.myapp.entity.Passport;
import com.mycom.myapp.entity.Person;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;

// OneToOne bi-directional
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
			// Passport find()
			// join 수행, Person 함게 가져 온다.
			// @OneToOne 의 default FetchType 이 EAGER
//			{
//				Passport passport = em.find(Passport.class, 1);
//				System.out.println(passport);
///*
//Hibernate: select p1_0.id,p1_0.number,p2_0.id,p2_0.name from Passport p1_0 left join Person p2_0 on p1_0.id=p2_0.passport where p1_0.id=?
//Passport [id=1, number=kor123]				
// */
//				try {
//					Thread.sleep(5000);
//				}catch(Exception e) {
//					e.printStackTrace();
//				}
//				
//				// passport 를 이용해서 person entity 를 사용하면 sql 다시 수행 X
//				System.out.println(passport.getPerson());
///*
//Person [id=1, name=홍길동]				
// */
//			}
			
			// #B
			// Person find()
			// join 수행, Passport 함게 가져 온다.
			// @OneToOne 의 default FetchType 이 EAGER
//			{
//				Person person = em.find(Person.class, 1);
//				System.out.println(person);
//				
///*
//Hibernate: select p1_0.id,p1_0.name,p2_0.id,p2_0.number from Person p1_0 left join Passport p2_0 on p2_0.id=p1_0.passport where p1_0.id=?
//Person [id=1, name=홍길동]				
// */
//				try {
//					Thread.sleep(5000);
//				}catch(Exception e) {
//					e.printStackTrace();
//				}
//				
//				// person 를 이용해서 passport entity 를 사용하면 sql 다시 수행 X
//				System.out.println(person.getPassport());
///*
//Passport [id=1, number=kor123]				
// */
//			}			
			
			// #C
			// Person find()
			// Person fetch LAZY 변경
			// join 수행 X, Person 만 가져 온다.
			{
				Person person = em.find(Person.class, 1);
				System.out.println(person);
				
/*
Hibernate: select p1_0.id,p1_0.name,p1_0.passport from Person p1_0 where p1_0.id=?
Person [id=1, name=홍길동]			
 */
				try {
					Thread.sleep(5000);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				// person 를 이용해서 passport entity 를 사용하면 sql 다시 수행
				// Passport 가 fetch 설정이 없으면 @OneToOne 의 fetch default 인 EAGER 로 인해 연관관계를 가지는 Person 과 join
				// 만약 Passport 의 fetch 설정이 LAZY 이면 join 대신 2개의 select sql 이 실행된다.
				System.out.println(person.getPassport());
/*
Hibernate: select p1_0.id,p1_0.number,p2_0.id,p2_0.name from Passport p1_0 left join Person p2_0 on p1_0.id=p2_0.passport where p1_0.id=?
Passport [id=1, number=kor123]	

만약 Passport 의 fetch 설정이 LAZY		
Hibernate: select p1_0.id,p1_0.number from Passport p1_0 where p1_0.id=?
Hibernate: select p1_0.id,p1_0.name,p1_0.passport from Person p1_0 where p1_0.passport=?
Passport [id=1, number=kor123]		
 */
			}	
			
			// #D
			// Passport find()
			// Passport fetch LAZY 변경, Person fetch EAGER 로 원복
			// Passport 를 위한 select 1 건 수행 + Person 과의 join 1 건 수행 : 총 2건 sql 수행
			// 연관관계에서 fetch 를 LAZY 로 변경해도 join sql 이 수행되는 경우 발생
			// mappedBy 에 의해 Non Owner Entity 인 경우, 해당 테이블에 Owner Entity 에 대한 컬럼이 X
			//   -> Passport 1건으로부터 이후 연관된 Person을 찾을 방법 X
			//   -> LAZY 정책을 수행하기 위해서는 미리 Passport 와 연관된 Person 을 찾아서 준비(궁여지책)
			// LAZY 에 대한 JPA 구현체의 대응 : Proxy 생성 <- null (person) 어렵다.
//			{
//				Passport passport = em.find(Passport.class, 1);
//				System.out.println(passport);
//				
///*
//Hibernate: select p1_0.id,p1_0.number from Passport p1_0 where p1_0.id=?
//Hibernate: select p1_0.id,p1_0.name,p2_0.id,p2_0.number from Person p1_0 left join Passport p2_0 on p2_0.id=p1_0.passport where p1_0.passport=?
//Passport [id=1, number=kor123]		
// */
//				try {
//					Thread.sleep(5000);
//				}catch(Exception e) {
//					e.printStackTrace();
//				}
//				
//				// passport 를 이용해서 person entity 를 사용하면 sql 다시 수행 X ( 위 미리 선 수행된 join 에 의해 가능 )
//				System.out.println(passport.getPerson());
///*
//Person [id=1, name=홍길동]		
// */
//			}			
			
			// #E
			// Passport find()
			// Person 의 toString() 에서 Passport 출력
			// Passport 의 toString() 에서 Person 출력
			// StackOverflowError 발생 ( toString() 의 순환구조 ) 
			// lombok ( @Data, @ToString 주의!!! )
//			{
//				Passport passport = em.find(Passport.class, 1);
//				System.out.println(passport);
//			}			
			em.getTransaction().commit();
		}finally {
			em.close();
		}
	}

}