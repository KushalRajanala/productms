package com.ordermanagement.gp8.product.repository;

import org.springframework.data.repository.CrudRepository;

import com.ordermanagement.gp8.product.entity.SubscribedProduct;
import com.ordermanagement.gp8.product.utility.Productpk;

public interface SubscribedProductRepository extends CrudRepository<SubscribedProduct, Productpk> {
	}
