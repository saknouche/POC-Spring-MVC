package com.sadev.app.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sadev.app.model.Category;
import com.sadev.app.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public Iterable<Category> getAllCategories(){
		return categoryRepository.findAll();
	}
	
	public Optional<Category> getCategoryById(Long id) {
		return categoryRepository.findById(id);
	}
	
	
	public Category createCategory(Category category) {
		if(category != null) {			
			Category savedCategory = categoryRepository.save(category);
			return savedCategory;
		}
		return null;
	}
	
	public void deleteCategoryById(Long id) {
		if(id != null) {
			categoryRepository.deleteById(id);
		}
	}
}
