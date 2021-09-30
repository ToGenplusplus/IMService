package com.example.IMS.StoreProduct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.example.IMS.Store.StoreService;

@Service
public class StoreProductService {

	private final StoreProductRepository storeProdRepo;
	private final StoreService storeSvc;

	@Autowired
	public StoreProductService(StoreProductRepository storeProdRepo, 
			StoreService storeSvc) {
		this.storeProdRepo = storeProdRepo;
		this.storeSvc = storeSvc;
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
