package com.example.IMS.Category;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/categories", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class CategoryController {
	
	private final CategoryService catSvc;

	protected CategoryController(CategoryService catSvc) {
		this.catSvc = catSvc;
	}
	
	@GetMapping(path = "/all")
	public List<Category> getAllProductCategories()
	{
		return catSvc.getAllProductCategories();
	}

}
