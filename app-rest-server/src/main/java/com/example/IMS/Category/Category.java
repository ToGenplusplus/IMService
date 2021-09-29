package com.example.IMS.Category;


import java.util.Set;

import javax.persistence.*;

import com.example.IMS.Product.Product;

import lombok.*;

@Entity
@Table(name = "category")
@Getter @Setter @NoArgsConstructor 
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false, name = "category_name", unique = true)
    private String categoryName;
	
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private Set<Product> products;

	public Category(String categoryName, Set<Product> products) {
		this.categoryName = categoryName;
		this.products = products;
	}


	
	
	

}
