package com.ordermanagement.gp8.product.service;

import java.util.List;

import com.ordermanagement.gp8.product.dto.ProductDTO;

import com.ordermanagement.gp8.product.exception.ProductException;

public interface ProductService {
	public ProductDTO getProductByName(String prodName) throws ProductException;
	public List<ProductDTO> getProductByCategory(String category) throws ProductException;
	public ProductDTO getProductByprodId(String prodid) throws ProductException;
	public List<ProductDTO> getAllProducts() throws ProductException;
    public String addProduct(ProductDTO productDTO) throws ProductException;
    public String deleteProduct(String prodId) throws ProductException;
	public Boolean updateStockOfProduct(String prodId, Integer quantity) throws ProductException;
	}
