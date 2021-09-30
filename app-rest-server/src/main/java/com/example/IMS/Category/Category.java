package com.example.IMS.Category;


import java.util.Set;

import javax.persistence.*;

import com.example.IMS.Product.Product;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Entity
@Table(name = "category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false, name = "category_name", unique = true)
    private String categoryName;
	
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<Product> products;
	
	protected Category(){}

	public Category(String categoryName, Set<Product> products) {
		this.categoryName = categoryName;
		this.products = products;
	}
	
	

	public Long getId() {
		return id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	


	
	
	

}
