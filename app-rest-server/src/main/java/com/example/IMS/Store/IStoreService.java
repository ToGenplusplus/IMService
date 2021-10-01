package com.example.IMS.Store;

import java.util.List;

public interface IStoreService {

	List<Store> getAllStoresInCompany();
	
	Store getStoreById(long storeId);
}
