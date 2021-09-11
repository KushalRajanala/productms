package com.ordermanagement.gp8.product.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ordermanagement.gp8.product.utility.Productpk;

@Entity
@Table(name = "subscribedProduct_table")
public class SubscribedProduct {
	
	@EmbeddedId
	private Productpk productId;

	public Productpk getProductId() {
		return productId;
	}

	public void setProductId(Productpk customId) {
		this.productId = customId;
	}
	
	

}
