package com.mycom.myapp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

// Person 1명당 Passport 1개를 가진다.
@Entity
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	// #1 ~ #4 까지 연습
//	@OneToOne
//	// JoinColumn 을 가진 Entity 가 관계에서 Ownership 을 가진다.
//	@JoinColumn(name="passport") // passport 컬럼명 명시적으로 지정
	
	// #5
//	@OneToOne(cascade = CascadeType.PERSIST)
//	@JoinColumn(name="passport")
		
	// #C
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="passport")
	private Passport passport;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}

	
	// toString 에 passport 를 사용하고 있다.
//	@Override
//	public String toString() {
//		return "Person [id=" + id + ", name=" + name + ", passport=" + passport + "]";
//	}
	
	// #C 테스트에서 아래의 코드 중 passport 제외
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}

}
