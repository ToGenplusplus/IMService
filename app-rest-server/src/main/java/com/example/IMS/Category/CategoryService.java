package com.example.IMS.Category;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService{
	
	private final CategoryRepository categoryRepo;

	@Autowired
	public CategoryService(CategoryRepository categoryRepo) {
		this.categoryRepo = categoryRepo;
	}
	
	@Override
	public List<Category> getAllProductCategories()
	{
		return categoryRepo.findAll();
	}
	
	@Override
	public Category getCategoryById(long categoryId)
	{
		if(!categoryRepo.findById(categoryId).isPresent()) return null;
		return categoryRepo.getById(categoryId);
	}
	
	

}
