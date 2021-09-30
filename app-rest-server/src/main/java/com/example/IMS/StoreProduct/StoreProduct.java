package com.example.IMS.StoreProduct;

import javax.persistence.*;

import com.example.IMS.Popularity;
import com.example.IMS.Product.Product;
import com.example.IMS.Store.Store;

import lombok.*;

@Entity
@Table(name = "store_product", uniqueConstraints = {
        @UniqueConstraint(name = "unique_store_prod",
        		columnNames = {"store_id","prod_id"})
})
@Getter @Setter @NoArgsConstructor 

public class StoreProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
	private Store store;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prod_id", nullable = false)
	private Product product;
	
	@Column(nullable = false, name = "prod_price")
	private Double price;
	
	@Column(nullable = false, name = "inventory_count")
	private int inventoryCount;
	
	@Column(name = "prod_popularity")
	@Enumerated(EnumType.STRING)
	private Popularity popularity;
	
	@Column(name = "prod_description")
	private String productDescription;
	
	@Column(name = "is_removed")
	private Boolean isRemoved;

	public StoreProduct(Store store, Product product, Double price, int inventoryCount,
			Popularity popularity, String productDescription, Boolean isRemoved) {
		this.store = store;
		this.product = product;
		this.price = price;
		this.inventoryCount = inventoryCount;
		this.popularity = popularity;
		this.productDescription = productDescription;
		this.isRemoved = isRemoved;
	}
	
	
	
	
	
	

}
