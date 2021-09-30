package com.example.IMS.Product;

import java.util.Set;

import javax.persistence.*;

import com.example.IMS.Category.Category;
import com.example.IMS.StoreProduct.StoreProduct;

import lombok.*;

@Entity
@Table(name = "product", uniqueConstraints = {
        @UniqueConstraint(name = "unique_upc_prod_name",
        		columnNames = {"upc_number","prod_name"})
        })
@Getter @Setter @NoArgsConstructor 
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
	private Category category;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private Set<StoreProduct> storeProducts;

	public Product(String productName, Long upcNumber, Category category, Set<StoreProduct> storeProducts) {
		this.productName = productName;
		this.upcNumber = upcNumber;
		this.category = category;
		this.storeProducts = storeProducts;
	}

	
	
	

}
