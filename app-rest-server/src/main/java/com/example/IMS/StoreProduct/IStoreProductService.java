package com.example.IMS.StoreProduct;

import java.util.List;

import com.example.IMS.ApiResponse.MethodReturnObject;

public interface IStoreProductService {
	
	MethodReturnObject<List<StoreProduct>> getAllProductsForStoreByStoreId(
			long storeId, String inStock, String rangeLower, 
			String rangeUpper, String rating, String categoryId);
	
	
	MethodReturnObject<StoreProduct> getAStoresProductByStoreIdAndProductId(long storeId, long productId);
	
	MethodReturnObject<StoreProduct> getAStoresProductByStoreIdAndProductIUpcNumber(long storeId, long upcNumber);
	
	MethodReturnObject<StoreProduct> addNewProductToStoreInventory(StoreProduct newStoreProd);

	MethodReturnObject<StoreProduct> updateInventoryProduct(StoreProduct newStoreProd);
	
	MethodReturnObject<Long> removeProductFromStoreInventoryByProductId(long storeId, long productId);
}
