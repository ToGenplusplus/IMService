package com.example.IMS.Product;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.IMS.Category.Category;
import com.example.IMS.Category.CategoryRepository;

@DataJpaTest
class ProductRepositoryTest {

	@Autowired
	private ProductRepository underTest;
	@Autowired
	private CategoryRepository catRepo;
	
	private List<Category> categories;
	
	@BeforeEach
	void setUp()
	{
		categories = generateCategories();
		catRepo.saveAll(categories);
		
		categories = catRepo.findAll();
	}
	
	@AfterEach
	void tearDown() throws Exception {
		underTest.deleteAll();
		catRepo.deleteAll();
	}

	@Test
	void test() {
//		fail("Not yet implemented");
	}
	
	@Test
	void canSaveAndRetrieveProducts()
	{
		List<Product> products = generateProducts();
		int numProds = products.size();
		
		underTest.saveAll(products);
		
		List<Product> fromDb = underTest.findAll();
		assertThat(fromDb.size()).isEqualTo(numProds);
	}
	
	@Test
	void givenProductIdReturnsProductWithGivenId()
	{
		List<Product> products = generateProducts();
		
		Product prod = products.get(3);
		underTest.save(prod);
		
		Product prodFromDb = underTest.getById(prod.getId());
		assertThat(prodFromDb.getId()).isEqualTo(prod.getId());
		assertThat(prodFromDb).isEqualTo(prod);
	}
	
	@Test
	void givenCategoryIdReturnsProductsInCategoryWithCategoryId()
	{
		List<Product> products = generateProducts();
		underTest.saveAll(products);
		
		List<Product> fromDb = underTest.findAll();
		Category cat = categories.get(2);
		
		fromDb.removeIf(prod -> prod.getCategory().getId() != cat.getId());
		
		List<Product> withCatId = underTest.getProductByCategoryId(cat.getId());
		assertThat(withCatId.size()).isEqualTo(fromDb.size());
		assertThat(withCatId).isEqualTo(fromDb);
		
	}
	
	private List<Product> generateProducts()
	{
		List<Product> products = new ArrayList<>();
		
		for (int i = 0; i < 5; i++)
		{
			products.add(new Product("Product - " + i, getRandom(100000000000L, 999999999999L),categories.get(i),null));
		}
		
		return products;
	}

	
	private List<Category> generateCategories()
	{
		List<Category> categories = new ArrayList<>();
		
		for (int i = 0; i < 5; i++)
		{
			categories.add(new Category("Category - " + i, null));
		}
		
		return categories;
	}
	
	private long getRandom(long min, long max)
	{
		return ((long) (Math.random()*(max - min))) + min;
	}

}
