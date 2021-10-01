package com.example.IMS.Product;

import java.util.List;

import com.example.IMS.ApiResponse.MethodReturnObject;

public interface IProductService {
	
	List<Product> getAllProducts();

	List<Product> getAllProductsByCategory(long categoryId);
	
	List<Product> getAllProductsByWithIds(List<Long> prodIds);
	
	MethodReturnObject<Product> addNewProduct(Product newProd);
	
	MethodReturnObject<Product> updateProduct(Product newProd);
}
