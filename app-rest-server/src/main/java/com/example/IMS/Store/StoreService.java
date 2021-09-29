package com.example.IMS.Store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
	
	private StoreRepository storeRepo;

	@Autowired
	public StoreService(StoreRepository storeRepo) {
		this.storeRepo = storeRepo;
	}
	
	
	//get store by id

}
