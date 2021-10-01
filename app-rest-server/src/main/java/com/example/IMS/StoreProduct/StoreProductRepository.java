package com.example.IMS.StoreProduct;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreProductRepository extends JpaRepository<StoreProduct, Long>{

	@Modifying
	@Query(value = "UPDATE store_product SET is_removed = b'01', date_removed = :date_removed WHERE store_id = :store_id AND prod_id = :product_id", 
			nativeQuery = true)
	int removeStoreProduct(@Param("store_id")long storeId,@ Param("product_id") long productId,@Param("date_removed") LocalDate dateRemoved);
}
