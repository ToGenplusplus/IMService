package com.example.IMS.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.IMS.ApiResponse.MethodReturnObject;
import com.example.IMS.Category.CategoryService;

@Service
public class ProductService {
	
	private final ProductRepository productRepo;
	private final CategoryService catSvc;

	@Autowired
	public ProductService(ProductRepository productRepo, CategoryService catSvc) {
		this.productRepo = productRepo;
		this.catSvc = catSvc;
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
	
	@Transactional
	public MethodReturnObject<Product> addNewProduct(Product newProd)
	{
		/**
		 * CHECKS
		 * 
		 * REQUIRED: CATEGORY, NAME, UPC NUMBER
		 * UNIQUE: UPC_NUMBER
		 */
		if (newProd.getCategory() == null || newProd.getProductName() == null || newProd.getUpcNumber() == null) 
		{
			
			return MethodReturnObject.of("request body missing category object, upcNumber or productName information");
		}
		
		if(newProd.getUpcNumber().toString().length() != 12) 
		{
			return MethodReturnObject.of("invalid upcNumber, upc's are 12 digits in length");
		}
		
		if(newProd.getCategory().getId() == null || catSvc.getCategoryById(newProd.getCategory().getId()) == null) 
		{
			return MethodReturnObject.of("category id is missing or category with provided id does not exist");
		}
		
		List<Product> allProds = getAllProducts();
		
		for (Product prod : allProds)
		{
			if (prod.getUpcNumber().equals(newProd.getUpcNumber()))
			{
				return MethodReturnObject.of(String.format("upcNumber %d already exists in system. ", newProd.getUpcNumber()));
			}
		}
		
		productRepo.saveAndFlush(newProd);
		return MethodReturnObject.of(newProd); 
	}
	
	@Transactional
	public MethodReturnObject<Product> updateProduct(Product newProd)
	{
		//can only update product name and upc_number
		if (newProd.getId() == null) return MethodReturnObject.of("id of product to update is missing from request body", null);
		
		Product prodToUpdate = productRepo.getById(newProd.getId());
		
		/**
		 * Todo
		 * check if newProd has upc_number set
		 * if it is set we wan't to make sure its a valid upc_number and its not already in the system
		 * if its valid set the prodToUpdate upc_number with new upc_number if its not valid don't do anything
		 */
		if (newProd.getUpcNumber() != null)
		{
			List<Product> allProduct = getAllProducts();
			boolean exists = false;

			for (Product prod : allProduct)
			{
				if (prod.getUpcNumber() == newProd.getUpcNumber() && prod.getId() != newProd.getId())
				{
					exists = true;
					break;
				}
			}
			//valid upc numbers are 12 digits
			if (!exists && newProd.getUpcNumber().toString().length() == 12)
			{
				prodToUpdate.setUpcNumber(newProd.getUpcNumber());
			}
		}
		
		//names can have duplicates since upc_numbers cannot
		prodToUpdate.setProductName(newProd.getProductName() != null ? newProd.getProductName() : prodToUpdate.getProductName());
		
		productRepo.saveAndFlush(prodToUpdate);
		return MethodReturnObject.of(null, prodToUpdate);
	}
	
	
}
