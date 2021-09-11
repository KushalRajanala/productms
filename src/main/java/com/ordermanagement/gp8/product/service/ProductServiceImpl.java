package com.ordermanagement.gp8.product.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ordermanagement.gp8.product.dto.ProductDTO;
import com.ordermanagement.gp8.product.entity.Product;
import com.ordermanagement.gp8.product.exception.ProductException;
import com.ordermanagement.gp8.product.repository.ProductRepository;

@Service(value = "productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	 ProductRepository productRepo;
	
	 //static int p;
	
	//static {
		//p=100;
	//}
	
	//get by name
		@Override
		public ProductDTO getProductByName(String ProdName) throws ProductException {
			Product prod = productRepo.findByProductName(ProdName);
			if(prod == null)
			throw new ProductException("Service.PRODUCT_NOT_AVAILABLE");
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProdId(prod.getProdId());
			productDTO.setProductName(prod.getProductName());
			productDTO.setPrice(prod.getPrice());
			productDTO.setImage(prod.getImage());
			productDTO.setSellerId(prod.getSellerId());
			productDTO.setStock(prod.getStock());
			productDTO.setDescription(prod.getDescription());
			productDTO.setCategory(prod.getCategory());
			productDTO.setSubCategory(prod.getSubCategory());
			productDTO.setProductRating(prod.getProductRating());
			return productDTO;
		}
		
		// get by prodid
		@Override
		public ProductDTO getProductByprodId(String prodid) throws ProductException {
			Product prod1 = productRepo.findByProdId(prodid);
			if(prod1 == null)
			throw new ProductException("No product with this id");
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProdId(prod1.getProdId());
			productDTO.setProductName(prod1.getProductName());
			productDTO.setPrice(prod1.getPrice());
			productDTO.setImage(prod1.getImage());
			productDTO.setSellerId(prod1.getSellerId());
			productDTO.setStock(prod1.getStock());
			productDTO.setDescription(prod1.getDescription());
			productDTO.setCategory(prod1.getCategory());
			productDTO.setSubCategory(prod1.getSubCategory());
			productDTO.setProductRating(prod1.getProductRating());
			return productDTO;
		}
		
		//get by category
		@Override
		public List<ProductDTO> getProductByCategory(String category) throws ProductException {
			List<Product> list = productRepo.findByCategory(category);
			if(list.isEmpty())
				throw new ProductException("No product with this category");
			List<ProductDTO> list1 = new ArrayList<>();
			for(Product prod : list)
			{
				ProductDTO productDTO = new ProductDTO();
				productDTO.setProdId(prod.getProdId());
				productDTO.setProductName(prod.getProductName());
				productDTO.setPrice(prod.getPrice());
				productDTO.setImage(prod.getImage());
				productDTO.setSellerId(prod.getSellerId());
				productDTO.setStock(prod.getStock());
				productDTO.setDescription(prod.getDescription());
				productDTO.setCategory(prod.getCategory());
				productDTO.setSubCategory(prod.getSubCategory());
				productDTO.setProductRating(prod.getProductRating());
				list1.add(productDTO);
			}
			return list1;
		}
		
		// get all products
		@Override
		public List<ProductDTO> getAllProducts() throws ProductException {
			
			List<Product> list = productRepo.findAll();
			if(list.isEmpty())
			throw new ProductException("No products available");
			List<ProductDTO> list1 = new ArrayList<>();
			list.forEach(list2 -> {
				ProductDTO product1 = new ProductDTO();
				product1.setCategory(list2.getCategory());
				product1.setDescription(list2.getDescription());
				product1.setImage(list2.getImage());
				product1.setPrice(list2.getPrice());
				product1.setProdId(list2.getProdId());
				product1.setProductName(list2.getProductName());
				product1.setProductRating(list2.getProductRating());
				product1.setSellerId(list2.getSellerId());
				product1.setStock(list2.getStock());
				product1.setSubCategory(list2.getSubCategory());
				list1.add(product1);
			}
			);
			return list1;
		}

   
		
   //add product	
	  @Override
	  public String addProduct(ProductDTO productDTO) throws ProductException {
		Product product = productRepo.findByProductName(productDTO.getProductName());
		if(product != null)
		throw new ProductException("Service.PRODUCT_AVAILABLE_ALREADY");
    	product = new Product();
		//String prodid = "P"+p++;
		//product.setProdId(prodid);
		product.setProdId(productDTO.getProdId());
		product.setProductName(productDTO.getProductName());
		product.setPrice(productDTO.getPrice());
		product.setImage(productDTO.getImage());
		product.setSellerId(productDTO.getSellerId());
		product.setStock(productDTO.getStock());
		product.setDescription(productDTO.getDescription());
		product.setCategory(productDTO.getCategory());
		product.setSubCategory(productDTO.getSubCategory());
		product.setProductRating(productDTO.getProductRating());
		productRepo.save(product);
		return "product added successfully with productId: "+ product.getProdId();
		//return product.getProdId();
	}

	
	
//deletion of product
	@Override
	public String deleteProduct(String prodId) throws ProductException {
		Product prod2 = productRepo.findByProdId(prodId);
		if(prod2 == null)
		throw new ProductException("Service.DELETE_PRODUCT_FAILED");
		productRepo.delete(prod2);
		return "product deletion success";
		}

//updation of stock
	@Override
	public Boolean updateStockOfProduct(String prodId, Integer quantity) throws ProductException {
		Optional<Product> optProd = productRepo.findById(prodId);
		Product product = optProd.orElseThrow(()-> new ProductException("There is no such product"));
		if(product.getStock()>=quantity) {
			product.setStock(product.getStock()-quantity);
			return true;
		}
		return false;		
	}
}
