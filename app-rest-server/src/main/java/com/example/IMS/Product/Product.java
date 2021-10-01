package com.example.IMS.Product;

import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import com.example.IMS.Category.Category;
import com.example.IMS.StoreProduct.StoreProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false, name = "prod_name")
	private String productName;
	
	@Column(nullable = false, name = "upc_number", unique = true, length = 12)
	private Long upcNumber;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
	private Category category;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private Set<StoreProduct> storeProducts;
	
	protected Product(){}

	public Product(String productName, Long upcNumber, Category category, Set<StoreProduct> storeProducts) {
		this.productName = productName;
		this.upcNumber = upcNumber;
		this.category = category;
		this.storeProducts = storeProducts;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id) && Objects.equals(productName, other.productName)
				&& Objects.equals(upcNumber, other.upcNumber);
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

	@JsonIgnore
	public Set<StoreProduct> getStoreProducts() {
		return storeProducts;
	}

	public void setStoreProducts(Set<StoreProduct> storeProducts) {
		this.storeProducts = storeProducts;
	}
	
	

	
	
	

}
