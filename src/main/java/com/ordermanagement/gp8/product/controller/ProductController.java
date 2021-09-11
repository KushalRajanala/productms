package com.ordermanagement.gp8.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.ordermanagement.gp8.product.dto.ProductDTO;
import com.ordermanagement.gp8.product.service.ProductService;

@RestController
//@CrossOrigin
@RequestMapping(value = "api")
public class ProductController {
	
	@Autowired
    ProductService prodservice;
	
	@Autowired
	 Environment environment;
	@Value("${user.uri}")
	String userUri;
	
//get by name
	@GetMapping(value = "/product/ByName/{prodName}")
	public ResponseEntity<ProductDTO> getByProdName(@PathVariable String prodName) {
		try {
			ProductDTO productDTO = prodservice.getProductByName(prodName);
			return new ResponseEntity<>(productDTO, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}
	
//get by id
	@GetMapping(value = "/product/ById/{prodid}")
	public ResponseEntity<ProductDTO> getById(@PathVariable String prodid) {
		try {
			ProductDTO productDTO = prodservice.getProductByprodId(prodid);
			return new ResponseEntity<>(productDTO, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	// get by category
	@GetMapping(value = "/product/ByCategory/{category}")
	public ResponseEntity<List<ProductDTO>> getByProductCategory(@PathVariable String category) {
		try {
			List<ProductDTO> productDTO = prodservice.getProductByCategory(category);
			return new ResponseEntity<List<ProductDTO>>(productDTO, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}
	
	// view all product
	@GetMapping(value = "/product/viewAllProducts")
	public ResponseEntity<List<ProductDTO>> viewAllProducts() {
		try {
			List<ProductDTO> list = prodservice.getAllProducts();
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}
	}

			
//add product	
	@PostMapping(value = "/product/add")
	public ResponseEntity<String> add(@RequestBody ProductDTO product){
		
		try {
			String successmsg = prodservice.addProduct(product);
			return new ResponseEntity<>(successmsg,HttpStatus.ACCEPTED);
		}
		catch(Exception exception)
		{
			return new ResponseEntity<>(environment.getProperty(exception.getMessage()),HttpStatus.UNAUTHORIZED);
		}
		
	}
	

	
	// delete product
	@DeleteMapping(value = "/product/delete/{prodId}")
	public ResponseEntity<String> deletionofProduct(@PathVariable String prodId) {

		try {
			String successmsg = prodservice.deleteProduct(prodId);
			return new ResponseEntity<>(successmsg, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(environment.getProperty(exception.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	
	// update stock
	@GetMapping(value = "/product/updateStock/{prodId}/{quantity}")
	public ResponseEntity<Boolean> updateStockofprod(@PathVariable String prodId, @PathVariable Integer quantity) {
		try {
			Boolean status = prodservice.updateStockOfProduct(prodId, quantity);
			return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}


//add to wishlist
@PostMapping(value = "/addWishlist/{buyerId}/{prodId}")
	public ResponseEntity<String> addTowishlist(@PathVariable String buyerId, @PathVariable String prodId){
		try {
		String success = new RestTemplate().postForObject(userUri+"api/buyer/addtowishlist/"+buyerId+"/"+prodId, null, String.class);
        return new ResponseEntity<>(success,HttpStatus.ACCEPTED);
	}
	catch(Exception exception)
	{
	String error = "error occured";
	if(exception.getMessage().equals("404 null"))
	{
	error = "product not found";
			}
	return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
		}	
}
//remove from wishlist
    @PostMapping(value = "/removeWishlist/{buyerId}/{prodId}")
public ResponseEntity<String> removeFromWishlist(@PathVariable String buyerId, @PathVariable String prodId) {
   try {
    String success = new RestTemplate().postForObject(userUri + "api/buyer/removewishlist/" + buyerId + "/" + prodId, null, String.class);
   return new ResponseEntity<>(success, HttpStatus.ACCEPTED);
	}
   catch (Exception exception) {
  String error = "error occured";
  if (exception.getMessage().equals("404 null")) {
	error = "product not found to remove";
}
return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
}

}	
