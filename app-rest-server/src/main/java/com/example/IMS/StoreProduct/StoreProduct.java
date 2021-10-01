package com.example.IMS.StoreProduct;

import java.time.LocalDate;

import javax.persistence.*;

import com.example.IMS.Popularity;
import com.example.IMS.Product.Product;
import com.example.IMS.Store.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "store_product", uniqueConstraints = {
        @UniqueConstraint(name = "unique_store_prod",
        		columnNames = {"store_id","prod_id"})
})
public class StoreProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
	private Store store;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
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
	
	@Column(name = "date_added", nullable = false)
	private LocalDate dateAdded;
	
	@Column(name = "date_removed")
	private LocalDate dateRemoved;
	
	@Column(name = "is_removed")
	private Boolean isRemoved;
	
	protected StoreProduct() {}

	
	public StoreProduct(Store store, Product product, Double price, int inventoryCount, Popularity popularity,
			String productDescription, LocalDate dateAdded, LocalDate dateRemoved, Boolean isRemoved) {
		super();
		this.store = store;
		this.product = product;
		this.price = price;
		this.inventoryCount = inventoryCount;
		this.popularity = popularity;
		this.productDescription = productDescription;
		this.dateAdded = dateAdded;
		this.dateRemoved = dateRemoved;
		this.isRemoved = isRemoved;
	}


	@JsonIgnore
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
	
	
	public LocalDate getDateAdded() {
		return dateAdded;
	}


	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded;
	}


	@JsonIgnore
	public LocalDate getDateRemoved() {
		return dateRemoved;
	}


	public void setDateRemoved(LocalDate dateRemoved) {
		this.dateRemoved = dateRemoved;
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
