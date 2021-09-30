package com.example.IMS.StoreProduct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public List<StoreProduct> getAllProductsForStoreByStoreId(long storeId)
	{
		if (storeSvc.getStoreById(storeId) == null) return null;
		List<StoreProduct> storeProducts = storeProdRepo.findAll();
		
		storeProducts.removeIf(prod -> (prod.getStore().getId() != storeId));
		
		return storeProducts;
	}
	
	public StoreProduct getAStoresProductByStoreIdAndProductId(long storeId, long productId)
	{
		if (storeSvc.getStoreById(storeId) == null) return null;
		List<StoreProduct> storeProducts = getAllProductsForStoreByStoreId(storeId);
			
		storeProducts.removeIf(prod -> (prod.getProduct().getId() != productId));
		
		return storeProducts.size() == 0 ? null : storeProducts.get(0);
	}
	
}
