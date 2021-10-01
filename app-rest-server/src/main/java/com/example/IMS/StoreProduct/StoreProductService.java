package com.example.IMS.StoreProduct;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.example.IMS.ApiResponse.MethodReturnObject;
import com.example.IMS.Product.Product;
import com.example.IMS.Product.ProductService;
import com.example.IMS.Store.StoreService;

@Service
public class StoreProductService implements IStoreProductService{

	private final StoreProductRepository storeProdRepo;
	private final StoreService storeSvc;
	private final ProductService prodSvc;
	
	private final String MISSING_STORE_ID = " Could not find store with id %d";

	@Autowired
	public StoreProductService(StoreProductRepository storeProdRepo, 
			StoreService storeSvc,
			ProductService prodSvc) {
		this.storeProdRepo = storeProdRepo;
		this.storeSvc = storeSvc;
		this.prodSvc = prodSvc;
	}
	
	@Override
	public MethodReturnObject<List<StoreProduct>> getAllProductsForStoreByStoreId(
			long storeId, String inStock, String rangeLower, 
			String rangeUpper, String rating, String categoryId)
	{
		if (storeSvc.getStoreById(storeId) == null) return MethodReturnObject.of(String.format(MISSING_STORE_ID,storeId));
		List<StoreProduct> storeProducts = storeProdRepo.findAll();
		
		storeProducts.removeIf(prod -> (prod.getStore().getId() != storeId || (prod.getIsRemoved() != null && prod.getIsRemoved())));
		
		if (inStock != null && inStock.toLowerCase().equals("true")) storeProducts.removeIf(prod -> (prod.getInventoryCount() < 1));
		
		if (rating != null) storeProducts.removeIf(prod -> {
			if(prod.getPopularity() != null) return !prod.getPopularity().name().equals(rating);
			return true;
		});
		
		if(categoryId != null) storeProducts.removeIf(prod-> {
			try
			{
				long paramCatId = Long.parseLong(categoryId);
				long prodCatId = prod.getProduct().getCategory().getId();
				
				return prodCatId != paramCatId;
			}catch( NumberFormatException nfe)
			{
				return false;
			}
		});
		
		if(rangeLower != null && rangeUpper != null) 
		{
			try {
			long lower = Long.parseLong(rangeLower);
			long upper = Long.parseLong(rangeUpper);
			storeProducts.removeIf(prod -> (prod.getPrice() < lower || prod.getPrice() > upper));
			
			}catch( NumberFormatException nfe)
			{
				return MethodReturnObject.of(nfe.getMessage());
			}
		}
		
		return MethodReturnObject.of(storeProducts);
	}
	
	private MethodReturnObject<List<StoreProduct>> getAllProductsForStoreByStoreId(long storeId)
	{
		if (storeSvc.getStoreById(storeId) == null) return MethodReturnObject.of(String.format(MISSING_STORE_ID,storeId));
		List<StoreProduct> storeProducts = storeProdRepo.findAll();
		
		storeProducts.removeIf(prod -> (prod.getStore().getId() != storeId || (prod.getIsRemoved() != null && prod.getIsRemoved())));
		
		return MethodReturnObject.of(storeProducts);
	}
	
	@Override
	public MethodReturnObject<StoreProduct> getAStoresProductByStoreIdAndProductId(long storeId, long productId)
	{

		MethodReturnObject<List<StoreProduct>>  mro = getAllProductsForStoreByStoreId(storeId);
		if (mro.getReturnMessage() != null) return MethodReturnObject.of(mro.getReturnMessage());
		List<StoreProduct> storeProducts = mro.getReturnObject();
			
		storeProducts.removeIf(prod -> (prod.getProduct().getId() != productId));
		
		if (storeProducts.size() == 0)
		{
			return MethodReturnObject.of(String.format("could not find store product with store id %d and product id %d",storeId, productId));
		}
		return MethodReturnObject.of(storeProducts.get(0));
	}
	
	@Override
	public MethodReturnObject<StoreProduct> getAStoresProductByStoreIdAndProductIUpcNumber(long storeId, long upcNumber)
	{
		MethodReturnObject<List<StoreProduct>>  mro = getAllProductsForStoreByStoreId(storeId);
		if (mro.getReturnMessage() != null) return MethodReturnObject.of(mro.getReturnMessage());
		List<StoreProduct> storeProducts = mro.getReturnObject();
			
		storeProducts.removeIf(prod -> (prod.getProduct().getUpcNumber() != upcNumber));
		
		if (storeProducts.size() == 0)
		{
			return MethodReturnObject.of(String.format("could not find store product with store id %d and upcNumber %d",storeId, upcNumber));
		}
		return MethodReturnObject.of(storeProducts.get(0));
	}
	
	@Override
	@Transactional
	public MethodReturnObject<StoreProduct> addNewProductToStoreInventory(StoreProduct newStoreProd)
	{
		/**
		 * Checks
		 * REQUIRED: PRODUCT, STORE
		 * prodSvc handles the creation of new product
		 */
		
		if (newStoreProd.getProduct() == null || newStoreProd.getStore() == null) return MethodReturnObject.of("request body missing product or store information");
		if (newStoreProd.getStore().getId() == null) return MethodReturnObject.of("store object in request body is missing id");
		
	
		MethodReturnObject<Product> mroForProd = prodSvc.addNewProduct(newStoreProd.getProduct());
		if (mroForProd.getReturnMessage() != null) return MethodReturnObject.of(mroForProd.getReturnMessage());
		
		if(newStoreProd.getPrice() < 0) newStoreProd.setPrice(0d);
		if(newStoreProd.getInventoryCount() < 0) newStoreProd.setInventoryCount(0);
		newStoreProd.setDateAdded(LocalDate.now());
		
		storeProdRepo.saveAndFlush(newStoreProd);
		
		return MethodReturnObject.of(newStoreProd);
	}
	
	@Override
	@Transactional
	public MethodReturnObject<StoreProduct> updateInventoryProduct(StoreProduct newStoreProd)
	{
		
		if (newStoreProd.getProduct() == null || newStoreProd.getStore() == null) return null;
		if (newStoreProd.getProduct().getId() == null || newStoreProd.getStore().getId() == null) return null;
		
		Product prod = newStoreProd.getProduct();
		
		long storeId = newStoreProd.getStore().getId();
		long prodId = prod.getId();
		
		MethodReturnObject<StoreProduct>  mroStoreProd = getAStoresProductByStoreIdAndProductId(storeId, prodId);
		if (mroStoreProd.getReturnMessage() != null) return MethodReturnObject.of(mroStoreProd.getReturnMessage());
		StoreProduct prodToUpdate = mroStoreProd.getReturnObject();
		
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
		return MethodReturnObject.of(prodToUpdate);
		
	}
	
	@Override
	@Transactional
	public MethodReturnObject<Long> removeProductFromStoreInventoryByProductId(long storeId, long productId)
	{
		MethodReturnObject<StoreProduct>  mro = getAStoresProductByStoreIdAndProductId(storeId, productId);
		if (mro.getReturnMessage() != null) return MethodReturnObject.of(mro.getReturnMessage());
		StoreProduct prodToRemove = mro.getReturnObject();
		
		storeProdRepo.removeStoreProduct(storeId, productId, LocalDate.now());
		return MethodReturnObject.of(prodToRemove.getProduct().getId());
	}
	
}
