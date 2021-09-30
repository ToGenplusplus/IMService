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
	public ApiResponse<List<StoreProduct>> getAllProductsForStoreByStoreId(@PathVariable("store_id") long storeId,
			@RequestParam(value ="inStock", required = false)String inStock)
	{
		List<StoreProduct> storeProds = storeProdSvc.getAllProductsForStoreByStoreId(storeId, inStock);
		if (storeProds == null) return ApiResponse.failure(String.format("Could not find store with id ' %d'  ", storeId));
		return ApiResponse.success(storeProds);
	}
	
	@GetMapping(path = "/store-product/by_id/{store_id}/{product_id}")
	public ApiResponse<StoreProduct> getAStoresProductByStoreIdAndProductId(@PathVariable("store_id")long storeId, @PathVariable("product_id") long productId)
	{
		StoreProduct storeProd = storeProdSvc.getAStoresProductByStoreIdAndProductId(storeId, productId);
		if (storeProd == null) return ApiResponse.failure(String.format("Could not retrieve product for store with id ' %d ' and product with id ' %d '  ", storeId, productId));
		return ApiResponse.success(storeProd);
	}
	
	@GetMapping(path = "/store-product/by_upc/{store_id}/{product_upc}")
	public ApiResponse<StoreProduct> getAStoresProductByStoreIdAndProductIUpcNumber(@PathVariable("store_id")long storeId, @PathVariable("product_upc") long upcNumber)
	{
		StoreProduct storeProd = storeProdSvc.getAStoresProductByStoreIdAndProductIUpcNumber(storeId, upcNumber);
		if (storeProd == null) return ApiResponse.failure(String.format("Could not retrieve product for store with id ' %d ' and product with UPC  ' %d '  ", storeId, upcNumber));
		return ApiResponse.success(storeProd);
	}
	
	@PutMapping(path = "/store-product/update")
	public ApiResponse<StoreProduct> updateInventoryProduct(@RequestBody StoreProduct storeProd)
	{
		StoreProduct updatedProd = storeProdSvc.updateInventoryProduct(storeProd);
		if(updatedProd == null) return ApiResponse.failure("Failed to update product, ensure data to update is valid");
		return ApiResponse.success(updatedProd);
	}
	
	@DeleteMapping(path = "/store-product/delete/by_id/{store_id}/{product_id}")
	public ApiResponse<Long> removeProductFromStoreInventoryByProductId(@PathVariable("store_id")long storeId, @PathVariable("product_id") long productId)
	{
		long idOfProdRemoved = storeProdSvc.removeProductFromStoreInventoryByProductId(storeId, productId);
		if (idOfProdRemoved == -1) return ApiResponse.failure(String.format("Could not remove product for store with id ' %d ' and product with id ' %d '  ", storeId, productId));
		return ApiResponse.success(idOfProdRemoved);
	}

}
