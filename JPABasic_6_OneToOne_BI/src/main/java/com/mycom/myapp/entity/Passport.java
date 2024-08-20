package com.mycom.myapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Passport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String number;
	
	// #1 ~ #2 , #A, #B, #C, #E
//	@OneToOne(mappedBy="passport") // OneToOne 관계의 Owner Entity Person 에서 @JoinColumn 의 field 이름 
	
	// #3
//	@OneToOne(mappedBy="passport", cascade = CascadeType.PERSIST) 
	
	// #D
	@OneToOne(mappedBy="passport", fetch = FetchType.LAZY)
	private Person person;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	@Override
	public String toString() {
		return "Passport [id=" + id + ", number=" + number + "]";
	}

	// #E
//	@Override
//	public String toString() {
//		return "Passport [id=" + id + ", number=" + number + ", person=" + person + "]";
//	}	
	
}
