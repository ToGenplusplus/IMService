package com.example.IMS.Product;

import java.util.Set;

import javax.persistence.*;

import com.example.IMS.Category.Category;
import com.example.IMS.StoreProduct.StoreProduct;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Entity
@Table(name = "product", uniqueConstraints = {
        @UniqueConstraint(name = "unique_upc_prod_name",
        		columnNames = {"upc_number","prod_name"})
        })
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false, name = "prod_name", unique = true)
	private String productName;
	
	@Column(nullable = false, name = "upc_number", unique = true, length = 12)
	private Long upcNumber;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
	@JsonBackReference
	private Category category;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<StoreProduct> storeProducts;
	
	protected Product(){}

	public Product(String productName, Long upcNumber, Category category, Set<StoreProduct> storeProducts) {
		this.productName = productName;
		this.upcNumber = upcNumber;
		this.category = category;
		this.storeProducts = storeProducts;
	}
	
	
	

	public Long getId() {
		return id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getUpcNumber() {
		return upcNumber;
	}

	public void setUpcNumber(Long upcNumber) {
		this.upcNumber = upcNumber;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<StoreProduct> getStoreProducts() {
		return storeProducts;
	}

	public void setStoreProducts(Set<StoreProduct> storeProducts) {
		this.storeProducts = storeProducts;
	}
	
	

	
	
	

}
