package com.example.IMS.Product;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.IMS.ApiResponse.ApiResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/products")
@AllArgsConstructor
public class ProductController {
	
	private ProductService prodService;
	
	@GetMapping(path = "/all")
	public ApiResponse<List<Product>> getAllProducts()
	{
		return ApiResponse.success(prodService.getAllProducts());
	}
	
	@GetMapping(path = "/by_category/{category_id}")
	public ApiResponse<List<Product>> getAllProductsByCategoryId(@PathVariable("category_id")long categoryId)
	{
		return ApiResponse.success(prodService.getAllProducts());
	}

}
