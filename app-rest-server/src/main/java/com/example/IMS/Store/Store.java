package com.example.IMS.Store;


import java.util.Set;

import javax.persistence.*;

import com.example.IMS.StoreProduct.StoreProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "store")
public class Store {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false, name = "store_name", unique = true)
    private String storeName;
	
	@OneToMany(mappedBy = "store", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private Set<StoreProduct> storeProducts;
	
	protected Store(){}
	
	
	//for testing purposes
	public Store(Long id, String storeName, Set<StoreProduct> storeProducts) {
		super();
		this.id = id;
		this.storeName = storeName;
		this.storeProducts = storeProducts;
	}



	public Store(String storeName, Set<StoreProduct> storeProducts) {
		this.storeName = storeName;
		this.storeProducts = storeProducts;
	}
	

	public Long getId() {
		return id;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@JsonIgnore
	public Set<StoreProduct> getStoreProducts() {
		return storeProducts;
	}

	public void setStoreProducts(Set<StoreProduct> storeProducts) {
		this.storeProducts = storeProducts;
	}
	
	

	

	

}
