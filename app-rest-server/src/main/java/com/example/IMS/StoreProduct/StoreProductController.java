package com.example.IMS.StoreProduct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.IMS.ApiResponse.ApiResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/store_products", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class StoreProductController {
	
	private final StoreProductService storeProdSvc;

	@Autowired
	public StoreProductController(StoreProductService storeProdSvc) {
		this.storeProdSvc = storeProdSvc;
	}
	
	@GetMapping(path = "/all/{store_id}")
	public ApiResponse<List<StoreProduct>> getAllProductsForStoreByStoreId(@PathVariable("store_id")long storeId)
	{
		List<StoreProduct> storeProds = storeProdSvc.getAllProductsForStoreByStoreId(storeId);
		if (storeProds == null) return ApiResponse.failure(String.format("Could not find store with id ' %d'  ", storeId));
		return ApiResponse.success(storeProds);
	}
	
	@GetMapping(path = "/product/{store_id}/{product_id}")
	public ApiResponse<StoreProduct> getAStoresProductByStoreIdAndProductId(@PathVariable("store_id")long storeId, @PathVariable("product_id") long productId)
	{
		StoreProduct storeProd = storeProdSvc.getAStoresProductByStoreIdAndProductId(storeId, productId);
		if (storeProd == null) return ApiResponse.failure(String.format("Could not retrieve product for store with id ' %d ' and product with id ' %d '  ", storeId, productId));
		return ApiResponse.success(storeProd);
	}
	

}
