package com.example.IMS.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.IMS.ApiResponse.ApiResponse;
import com.example.IMS.ApiResponse.MethodReturnObject;

@RestController
@RequestMapping(path = "api/v1/products", produces = "application/json; charset=UTF-8")
public class ProductController {
	
	private final IProductService prodSvc;
	private final String FAIL = "OPERATION FAILED : ";
	
	
	@Autowired
	public ProductController(IProductService prodSvc) {
		this.prodSvc = prodSvc;
	}

	@GetMapping(path = "/all")
	@ResponseBody
	public ApiResponse<List<Product>> getAllProducts()
	{
		return ApiResponse.success(prodSvc.getAllProducts());
	}
	
	@GetMapping(path = "/by_category/{category_id}")
	public ApiResponse<List<Product>> getAllProductsByCategoryId(@PathVariable("category_id")long categoryId)
	{
		return ApiResponse.success(prodSvc.getAllProducts());
	}
	
	@PostMapping(path = "/product/new")
	public ApiResponse<Product> addNewProduct(@RequestBody Product newProd)
	{
		MethodReturnObject<Product>  mro = prodSvc.addNewProduct(newProd);
		Product newProdAdded = mro.getReturnObject() != null ? mro.getReturnObject() : null;
		if (newProdAdded == null) return ApiResponse.failure(FAIL + mro.getReturnMessage());
		return ApiResponse.success(newProdAdded);
	}
	
	@PutMapping(path = "/product/update")
	public ApiResponse<Product> getAllProductsByCategoryId(@RequestBody Product product)
	{
		MethodReturnObject<Product>  mro = prodSvc.updateProduct(product);
		Product updatedProduct = mro.getReturnMessage() == null ? mro.getReturnObject() : null;
		if(updatedProduct == null) return ApiResponse.failure(FAIL + mro.getReturnMessage());
		return ApiResponse.success(updatedProduct);
	}

}
