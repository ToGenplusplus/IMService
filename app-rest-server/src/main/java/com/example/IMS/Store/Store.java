package com.example.IMS.Store;


import java.util.Set;

import javax.persistence.*;

import com.example.IMS.StoreProduct.StoreProduct;

import lombok.*;

@Entity
@Table(name = "store")
@Getter @Setter @NoArgsConstructor 
public class Store {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false, name = "store_name", unique = true)
    private String storeName;
	
	@OneToMany(mappedBy = "store", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private Set<StoreProduct> storeProducts;

	public Store(String storeName, Set<StoreProduct> storeProducts) {
		this.storeName = storeName;
		this.storeProducts = storeProducts;
	}

	

	

}
