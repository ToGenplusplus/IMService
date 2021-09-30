package com.example.IMS.Store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IMS.ApiResponse.ApiResponse;

@Service
public class StoreService {
	
	private final StoreRepository storeRepo;

	@Autowired
	public StoreService(StoreRepository storeRepo) {
		this.storeRepo = storeRepo;
	}
	
	/**
	 * return all stores information for a company
	 * @return
	 */
	public List<Store> getAllStoresInCompany()
	{
		return storeRepo.findAll();
	}
	
	/**
	 * given a store id, return the store object associted with that Id
	 * @param storeId
	 * @return Store
	 */
	public Store getStoreById(long storeId)
	{
		if (!storeRepo.findById(storeId).isPresent()) return null;
		
		return storeRepo.getById(storeId);
	}

}
