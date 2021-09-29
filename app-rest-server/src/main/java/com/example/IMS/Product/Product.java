package com.example.IMS.Product;

import java.util.Set;

import javax.persistence.*;

import com.example.IMS.Category.Category;
import com.example.IMS.StoreProduct.StoreProduct;

import lombok.*;

@Entity
@Table(name = "product")
@Getter @Setter @NoArgsConstructor 
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false, name = "upc_number", unique = true, length = 12)
	private Long upcNumber;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
	private Category category;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private Set<StoreProduct> storeProducts;

	public Product(Long upcNumber, Category category, Set<StoreProduct> storeProducts) {
		this.upcNumber = upcNumber;
		this.category = category;
		this.storeProducts = storeProducts;
	}
	
	

}
