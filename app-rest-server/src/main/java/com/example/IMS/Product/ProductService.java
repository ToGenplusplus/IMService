package com.example.IMS.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.IMS.ApiResponse.MethodReturnObject;
import com.example.IMS.Category.ICategoryService;

@Service
public class ProductService implements IProductService{
	
	private final ProductRepository productRepo;
	private final ICategoryService catSvc;

	@Autowired
	public ProductService(ProductRepository productRepo, ICategoryService catSvc) {
		this.productRepo = productRepo;
		this.catSvc = catSvc;
	}
	
	@Override
	public List<Product> getAllProducts()
	{
		return productRepo.findAll();
	}
	
	@Override
	public List<Product> getAllProductsByCategory(long categoryId)
	{
		return productRepo.getProductByCategoryId(categoryId);
	}
	
	@Override
	public List<Product> getAllProductsByWithIds(List<Long> prodIds)
	{
		return productRepo.findAllById(prodIds);
	}
	
	@Override
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
	
	@Override
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
