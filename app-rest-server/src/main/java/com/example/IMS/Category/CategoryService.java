package com.example.IMS.Category;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	
	private final CategoryRepository categoryRepo;

	@Autowired
	public CategoryService(CategoryRepository categoryRepo) {
		this.categoryRepo = categoryRepo;
	}
	
	public Category getCategoryById(long categoryId)
	{
		if(!categoryRepo.findById(categoryId).isPresent()) return null;
		return categoryRepo.getById(categoryId);
	}
	
	

}
