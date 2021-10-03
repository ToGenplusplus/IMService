package com.example.IMS.Store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.IMS.ApiResponse.ApiResponse;


@RestController
@RequestMapping(path = "api/v1/stores", produces = "application/json; charset=UTF-8")
public class StoreController {
	
	private final IStoreService storeSvc;

	@Autowired
	public StoreController(IStoreService storeSvc) {
		this.storeSvc = storeSvc;
	}
	
	@GetMapping(path = "/all")
	public ApiResponse<List<Store>> getAllStores()
	{
		return ApiResponse.success(storeSvc.getAllStoresInCompany());
	}
	
	@GetMapping(path = "/store/{store_id}")
	public ApiResponse<Store> getStoreByStoreId(@PathVariable("store_id")long storeId)
	{
		Store store = storeSvc.getStoreById(storeId);
		if (store == null) return ApiResponse.failure(String.format("Could not find store with id ' %d'  ", storeId));
		
		return ApiResponse.success(store);
	}
	
	

}
