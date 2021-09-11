package com.ordermanagement.gp8.product.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ordermanagement.gp8.product.entity.Product;

public interface ProductRepository extends CrudRepository<Product, String> {
	public Product findByProductName(String prodName);
	public List<Product> findByCategory(String category);
	public Product findByProdId(String prodId);
	public List<Product> findAll();

}
