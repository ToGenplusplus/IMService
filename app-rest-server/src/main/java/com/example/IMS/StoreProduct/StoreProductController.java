package com.example.IMS.StoreProduct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.IMS.ApiResponse.ApiResponse;
import com.example.IMS.ApiResponse.MethodReturnObject;


@RestController
@RequestMapping(path = "api/v1/store-products", produces = "application/json; charset=UTF-8")
public class StoreProductController {
	
	private final StoreProductService storeProdSvc;
	private final String FAIL = "OPERATION FAILED : ";

	@Autowired
	public StoreProductController(StoreProductService storeProdSvc) {
		this.storeProdSvc = storeProdSvc;
	}
	
	@GetMapping(path = "/all/{store_id}")
	public ApiResponse<List<StoreProduct>> getAllProductsForStoreByStoreId(@PathVariable("store_id") long storeId,
			@RequestParam(value ="inStock", required = false)String inStock,
			@RequestParam(value ="rangeLower", required = false)String rangeLower,
			@RequestParam(value ="rangeUpper", required = false)String rangeUpper,
			@RequestParam(value ="rating", required = false)String rating,
			@RequestParam(value ="categoryId", required = false)String categoryId)
	{
		MethodReturnObject<List<StoreProduct>>  mro = storeProdSvc.getAllProductsForStoreByStoreId(storeId, inStock, rangeLower, rangeUpper, rating, categoryId);
		List<StoreProduct> storeProds = mro.getReturnObject() != null ? mro.getReturnObject() : null;
		if (storeProds == null) return ApiResponse.failure(FAIL + mro.getReturnMessage());
		return ApiResponse.success(storeProds);
	}
	
	@GetMapping(path = "/store-product/by_id/{store_id}/{product_id}")
	public ApiResponse<StoreProduct> getAStoresProductByStoreIdAndProductId(@PathVariable("store_id")long storeId, @PathVariable("product_id") long productId)
	{
		MethodReturnObject<StoreProduct>  mro = storeProdSvc.getAStoresProductByStoreIdAndProductId(storeId, productId);
		StoreProduct storeProd = mro.getReturnObject() != null ? mro.getReturnObject() : null;
		if (storeProd == null) return ApiResponse.failure(FAIL + mro.getReturnMessage());
		return ApiResponse.success(storeProd);
	}
	
	@GetMapping(path = "/store-product/by_upc/{store_id}/{product_upc}")
	public ApiResponse<StoreProduct> getAStoresProductByStoreIdAndProductIUpcNumber(@PathVariable("store_id")long storeId, @PathVariable("product_upc") long upcNumber)
	{
		MethodReturnObject<StoreProduct>  mro = storeProdSvc.getAStoresProductByStoreIdAndProductIUpcNumber(storeId, upcNumber);
		StoreProduct storeProd = mro.getReturnObject() != null ? mro.getReturnObject() : null;
		if (storeProd == null) return ApiResponse.failure(FAIL + mro.getReturnMessage());
		return ApiResponse.success(storeProd);
	}
	
	@PostMapping(path = "/store-product/new")
	public ApiResponse<StoreProduct> addNewProduct(@RequestBody StoreProduct newStoreProd)
	{
		MethodReturnObject<StoreProduct>  mro = storeProdSvc.addNewProductToStoreInventory(newStoreProd);
		StoreProduct newProdAdded = mro.getReturnObject() != null ? mro.getReturnObject() : null;
		if (newProdAdded == null) return ApiResponse.failure(FAIL + mro.getReturnMessage());
		return ApiResponse.success(newProdAdded);
	}
	
	@PutMapping(path = "/store-product/update")
	public ApiResponse<StoreProduct> updateInventoryProduct(@RequestBody StoreProduct storeProd)
	{
		MethodReturnObject<StoreProduct>  mro = storeProdSvc.updateInventoryProduct(storeProd);
		StoreProduct updatedProd = mro.getReturnObject() != null ? mro.getReturnObject() : null;
		if(updatedProd == null) return ApiResponse.failure(FAIL + mro.getReturnMessage());
		return ApiResponse.success(updatedProd);
	}
	
	@DeleteMapping(path = "/store-product/delete/by_id/{store_id}/{product_id}")
	public ApiResponse<Long> removeProductFromStoreInventoryByProductId(@PathVariable("store_id")long storeId, @PathVariable("product_id") long productId)
	{
		MethodReturnObject<Long>  mro = storeProdSvc.removeProductFromStoreInventoryByProductId(storeId, productId);
		long idOfProdRemoved = mro.getReturnObject() != null ? mro.getReturnObject() : null;
		if (idOfProdRemoved == -1) return ApiResponse.failure(FAIL + mro.getReturnMessage());
		return ApiResponse.success(idOfProdRemoved);
	}

}
