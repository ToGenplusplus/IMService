package com.example.IMS.StoreProduct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Service;

import com.example.IMS.Popularity;
import com.example.IMS.ApiResponse.MethodReturnObject;
import com.example.IMS.Product.Product;
import com.example.IMS.Product.ProductService;
import com.example.IMS.Store.StoreService;

@Service
public class StoreProductService {

	private final StoreProductRepository storeProdRepo;
	private final StoreService storeSvc;
	private final ProductService prodSvc;

	@Autowired
	public StoreProductService(StoreProductRepository storeProdRepo, 
			StoreService storeSvc,
			ProductService prodSvc) {
		this.storeProdRepo = storeProdRepo;
		this.storeSvc = storeSvc;
		this.prodSvc = prodSvc;
	}
	
	public List<StoreProduct> getAllProductsForStoreByStoreId(long storeId, String inStock)
	{
		if (storeSvc.getStoreById(storeId) == null) return null;
		List<StoreProduct> storeProducts = storeProdRepo.findAll();
		
		storeProducts.removeIf(prod -> (prod.getStore().getId() != storeId || (prod.getIsRemoved() != null && prod.getIsRemoved())));
		
		if (inStock != null && inStock.toLowerCase().equals("true")) storeProducts.removeIf(prod -> (prod.getInventoryCount() < 1));
		
		return storeProducts;
	}
	
	public StoreProduct getAStoresProductByStoreIdAndProductId(long storeId, long productId)
	{
		if (storeSvc.getStoreById(storeId) == null) return null;
		List<StoreProduct> storeProducts = getAllProductsForStoreByStoreId(storeId, null);
			
		storeProducts.removeIf(prod -> (prod.getProduct().getId() != productId));
		
		return storeProducts.size() == 0 ? null : storeProducts.get(0);
	}
	
	public StoreProduct getAStoresProductByStoreIdAndProductIUpcNumber(long storeId, long upcNumber)
	{
		if (storeSvc.getStoreById(storeId) == null) return null;
		List<StoreProduct> storeProducts = getAllProductsForStoreByStoreId(storeId, null);
			
		storeProducts.removeIf(prod -> (prod.getProduct().getUpcNumber() != upcNumber));
		
		return storeProducts.size() == 0 ? null : storeProducts.get(0);
	}
	
	@Transactional
	public StoreProduct addNewProductToStoreInventory(StoreProduct newStoreProd)
	{
		/**
		 * Checks
		 * REQUIRED: CATEGORY, PRODUCT, STORE
		 * new product name must not exist in inventory
		 * upc must not already exist in inventory
		 * 
		 */
		
		return null;
	}
	
	@Transactional
	public StoreProduct updateInventoryProduct(StoreProduct newStoreProd)
	{
		String errMessage = null;
		
		if (newStoreProd.getProduct() == null || newStoreProd.getStore() == null) return null;
		if (newStoreProd.getProduct().getId() == null || newStoreProd.getStore().getId() == null) return null;
		
		Product prod = newStoreProd.getProduct();
		
		long storeId = newStoreProd.getStore().getId();
		long prodId = prod.getId();
		
		StoreProduct prodToUpdate = getAStoresProductByStoreIdAndProductId(storeId, prodId);
		
		Product updatedProduct = null;
		
		//this checks if we need to update the product entity associated with this store product
		if((prod.getProductName() != null && !prod.getProductName().equals(prodToUpdate.getProduct().getProductName()))
				|| (prod.getUpcNumber() != null && prod.getUpcNumber().equals(prodToUpdate.getProduct().getUpcNumber())))
		{
			MethodReturnObject<Product>  mro = prodSvc.updateProduct(prod);
			updatedProduct = mro.getReturnMessage() == null ? mro.getReturnObject() : null;
		}
		/**
		 * can edit product price, description, rating, inventory_count
		 * price cannot be negative, rating must be valid popularity rating, inventory count cannot be negative
		 */
		prodToUpdate.setProduct(updatedProduct != null ? updatedProduct : prodToUpdate.getProduct());
		prodToUpdate.setPrice(newStoreProd.getPrice() >= 0 ? newStoreProd.getPrice() : prodToUpdate.getPrice());
		prodToUpdate.setProductDescription(newStoreProd.getProductDescription() != null ? newStoreProd.getProductDescription() : prodToUpdate.getProductDescription());
		prodToUpdate.setInventoryCount(newStoreProd.getInventoryCount() >= 0 ? newStoreProd.getInventoryCount() : prodToUpdate.getInventoryCount());
		prodToUpdate.setPopularity(newStoreProd.getPopularity() != null ? newStoreProd.getPopularity() : prodToUpdate.getPopularity());
		
		storeProdRepo.saveAndFlush(prodToUpdate);
		return prodToUpdate;
		
	}
	
	/**
	 * 
	 * @param storeId
	 * @param productId
	 * @ return product Id of product removed from inventory
	*/
	@Transactional
	public long removeProductFromStoreInventoryByProductId(long storeId, long productId)
	{
		StoreProduct prodToRemove = getAStoresProductByStoreIdAndProductId(storeId, productId);
		if (prodToRemove == null) return -1;
		
		storeProdRepo.removeStoreProduct(storeId, productId);
		return prodToRemove.getProduct().getId();
	}
	
}
