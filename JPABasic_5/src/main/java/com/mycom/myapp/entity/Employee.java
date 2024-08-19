package com.mycom.myapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Key 생성
// 운영 시는 DB 에 테이블 설계에 따라 테이블 생성하고, 그 DBMS 와 테이블에 맞는 Key 설정
@Entity
@Table(name = "employee")
public class Employee {
	
	@Id
	@Column(name="id")
	// jpa 의 기본 전략에 의존, MySQL 은 별도의 seq 테이블 생성, 관리 <= MySQL은 auto increment 지원 이걸 사용하는 것이 best
//	@GeneratedValue(strategy = GenerationType.AUTO) 
	
	// DMMS 의 auto increment 기능 사용, MySQL 에 최적
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	
	// Oracle 에 최적
//	@GeneratedValue(strategy = GenerationType.SEQUENCE) 
	
	// 마지막 옵션(거의 사용 안함)
//	@GeneratedValue(strategy = GenerationType.TABLE) 
	
	// ex) id 를 String 으로 해줘야된다.
//	@GeneratedValue(strategy = GenerationType.UUID) 
	private int id;
	private String name;
	private String address;
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", address=" + address + "]";
	}	
	
}
