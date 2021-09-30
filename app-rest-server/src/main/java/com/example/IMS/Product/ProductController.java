package com.example.IMS.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.IMS.ApiResponse.ApiResponse;

@RestController
@RequestMapping(path = "api/v1/products", produces = "application/json; charset=UTF-8")
public class ProductController {
	
	private final ProductService prodService;
	
	
	@Autowired
	public ProductController(ProductService prodService) {
		super();
		this.prodService = prodService;
	}

	@GetMapping(path = "/all")
	@ResponseBody
	public ApiResponse<List<Product>> getAllProducts()
	{
		return ApiResponse.success(prodService.getAllProducts());
	}
	
	@GetMapping(path = "/by_category/{category_id}")
	public ApiResponse<List<Product>> getAllProductsByCategoryId(@PathVariable("category_id")long categoryId)
	{
		return ApiResponse.success(prodService.getAllProducts());
	}
	
	@PutMapping(path = "/product/update")
	public ApiResponse<Product> getAllProductsByCategoryId(@RequestBody Product product)
	{
		Product updatedProduct = prodService.updateProduct(product);
		if(updatedProduct == null || !product.equals(updatedProduct)) return ApiResponse.failure("Could not update product, check values to make sure they are valid");
		return ApiResponse.success(updatedProduct);
	}

}
