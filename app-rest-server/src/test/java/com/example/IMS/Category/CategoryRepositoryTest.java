package com.example.IMS.Category;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class CategoryRepositoryTest {
	
	@Autowired
	private CategoryRepository underTest;

	@AfterEach
	void tearDown() throws Exception {
		underTest.deleteAll();
	}

	@Test
	void test() {
//		fail("Not yet implemented");
	}
	
	@Test
	void canSaveAndRetrieveCategories()
	{
		List<Category> categories = generateCategories();
		
		int numCats = categories.size();
		
		underTest.saveAll(categories);
		
		List<Category> fromDb = underTest.findAll();
		assertThat(fromDb.size()).isEqualTo(numCats);
	}
	
	@Test
	void givenCategoryIdReturnsCategoryWithGivenId()
	{
		List<Category> categories = generateCategories();
		
		Category cat = categories.get(1);
		underTest.save(cat);
		
		Category catFromDb = underTest.getById(cat.getId());
		
		assertThat(catFromDb.getId()).isEqualTo(cat.getId());
		assertThat(catFromDb).isEqualTo(cat);
		
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

}
