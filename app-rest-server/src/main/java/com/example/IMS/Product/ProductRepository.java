package com.example.IMS.Product;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query(value = "SELECT * FROM product p WHERE p.category_id = :category_id;", nativeQuery = true)
	List<Product > getProductByCategoryId(@Param("category_id")long id);
	
}
