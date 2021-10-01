package com.example.IMS.Category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/categories", produces = "application/json; charset=UTF-8")
public class CategoryController {
	
	private final CategoryService catSvc;

	@Autowired
	protected CategoryController(CategoryService catSvc) {
		this.catSvc = catSvc;
	}
	
	@GetMapping(path = "/all")
	public List<Category> getAllProductCategories()
	{
		return catSvc.getAllProductCategories();
	}

}
