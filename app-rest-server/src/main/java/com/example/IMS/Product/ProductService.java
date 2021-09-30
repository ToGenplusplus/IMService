package com.example.IMS.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	private ProductRepository productRepo;

	@Autowired
	public ProductService(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}
	
	/**
	 * get all products in database
	 * @return List<Product>
	 */
	public List<Product> getAllProducts()
	{
		return productRepo.findAll();
	}
	
	/**
	 * given a category id, return all products the given categoryId
	 * @param categoryId
	 * @return List<Product>
	 */
	public List<Product> getAllProductsByCategory(long categoryId)
	{
		return productRepo.getProductByCategoryId(categoryId);
	}
	
	/**
	 * given a  list of id's return a list of products associated with those ids
	 * @param prodIds
	 * @return List<Product>
	 */
	public List<Product> getAllProductsByWithIds(List<Long> prodIds)
	{
		return productRepo.findAllById(prodIds);
	}
	
	
}
