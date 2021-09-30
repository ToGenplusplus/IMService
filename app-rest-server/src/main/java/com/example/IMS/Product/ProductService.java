package com.example.IMS.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
	
	private final ProductRepository productRepo;

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
	
	@Transactional
	public Product updateProduct(Product newProd)
	{
		//can only update product name and upc_number
		if (newProd.getId() == null) return null;
		
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
		return prodToUpdate;
	}
	
	
}
