package com.mycom.myapp.entity.key;

import java.io.Serializable;
import java.util.Objects;

// 복합키를 표현
// @IdClass 사용될 조건
//	public class
//  기본생성자
//  equals() & hashCode() override
//  Serializable
public class ProductKey implements Serializable {
	
	private String code; // 제품 코드(PK)
	private long number; // 제품 번호(PK)
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(code, number);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductKey other = (ProductKey) obj;
		return Objects.equals(code, other.code) && number == other.number;
	}
	
	
}
