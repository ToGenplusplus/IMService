package com.example.IMS.StoreProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreProductService {

	private final StoreProductRepository storeProdRepo;

	@Autowired
	public StoreProductService(StoreProductRepository storeProdRepo) {
		this.storeProdRepo = storeProdRepo;
	}
	
}
