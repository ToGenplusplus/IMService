package com.example.IMS.StoreProduct;

import javax.persistence.*;

import com.example.IMS.Popularity;
import com.example.IMS.Product.Product;
import com.example.IMS.Store.Store;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Table(name = "store_product", uniqueConstraints = {
        @UniqueConstraint(name = "unique_store_prod",
        		columnNames = {"store_id","prod_id"})
})
@Getter @Setter
public class StoreProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
	@JsonBackReference
	private Store store;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prod_id", nullable = false)
	@JsonBackReference
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
	
	protected StoreProduct() {}

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

	public Long getId() {
		return id;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getInventoryCount() {
		return inventoryCount;
	}

	public void setInventoryCount(int inventoryCount) {
		this.inventoryCount = inventoryCount;
	}

	public Popularity getPopularity() {
		return popularity;
	}

	public void setPopularity(Popularity popularity) {
		this.popularity = popularity;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	//used internally for determining whether product has been "removed" from a stores inventory or not
	@JsonIgnore
	public Boolean getIsRemoved() {
		return isRemoved;
	}

	public void setIsRemoved(Boolean isRemoved) {
		this.isRemoved = isRemoved;
	}
	
	
	
	
	
	
	
	

}
