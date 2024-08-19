package com.mycom.myapp.entity.key;

import java.io.Serializable;
import java.util.Objects;

public class OrderKey implements Serializable{

	private int productId; // 제품 ID
	private int customerId; // 고객 ID
	
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
	@Override
	public int hashCode() {
		return Objects.hash(customerId, productId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderKey other = (OrderKey) obj;
		return customerId == other.customerId && productId == other.productId;
	}
	
}
