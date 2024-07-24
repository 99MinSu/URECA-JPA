package com.mycom.myapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Entity class 에 대응하는 table 명이 다르거나, field 에 대응하는 colum 명이 다르면
// @Table, @Colum 을 이용해서 적용할 수 있다.
@Entity
@Table(name = "employee")
public class Employee {
	
	@Id
	@Column(name="id")
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
