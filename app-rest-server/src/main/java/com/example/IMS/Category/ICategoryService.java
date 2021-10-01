package com.example.IMS.Category;

import java.util.List;

public interface ICategoryService {

	List<Category> getAllProductCategories();
	
	Category getCategoryById(long categoryId);
}
