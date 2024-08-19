package com.mycom.myapp.entity;

import com.mycom.myapp.entity.key.OrderKey;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
@IdClass(OrderKey.class)
public class Order {
	@Id
	@Column(name="product_id")
	private int productId; // 제품 ID
	@Id
	@Column(name="customer_id")
	private int customerId; // 고객 ID
	
	private int orderCnt; // 주문 수량

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getOrderCnt() {
		return orderCnt;
	}

	public void setOrderCnt(int orderCnt) {
		this.orderCnt = orderCnt;
	}

	@Override
	public String toString() {
		return "Order [productId=" + productId + ", customerId=" + customerId + ", orderCnt=" + orderCnt + "]";
	}
	
}

// productId, customerId 복합키 구성
// 테이블명 : orders
// key coulum
// 	product_id
//  customer_id

// 위 Order 클래스를 JPA Entity 만들고, 복합키를 이용한 persist, find 예제 작성