package com.sadev.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.sadev.app.model.Category;
import com.sadev.app.repository.CategoryRepository;
import com.sadev.app.service.CategoryService;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping("/categories")
	public String home(Model model){
		
		Iterable<Category> categories =  categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "homeCategory";
	}
	
    @GetMapping("/addCategory")
    public String addCategory(Model model){
        return "addCategory";
    }

  
    @PostMapping("/addCategory")
    public RedirectView add(@RequestParam("name") String name){
    	if(name != null) {    		
    		categoryService.createCategory(new Category(name));
    		return new RedirectView("categories");
    	} 
    	return null; 
    }
    
  @GetMapping("/updateCategory/{id}")
  public String updateCategory(@PathVariable("id") Integer id,  Model model){
  
  	
  	Optional<Category> optCategory = categoryService.getCategoryById(id);
  	Category category = new Category();
  	
  	if(optCategory.isPresent()) {
  		category = optCategory.get();
  	}
  	model.addAttribute("category", category);
  	
      return "updateCategory";
  }

    
    @PostMapping("/updateCategory")
    public RedirectView update(@RequestParam("name") String name, @RequestParam("id") String categoryId){
   
    	Integer id = null;
    	if(categoryId != null) {
    		id = Integer.parseInt(categoryId);
    	}
    	
    	Category updatedCategory = new Category();
    	Optional<Category> optCategory = categoryService.getCategoryById(id);
    	if(optCategory.isPresent()) {
    		updatedCategory = optCategory.get();
    	}
    	if(name != null) {
    		updatedCategory.setName(name);
    	}
       categoryRepository.save(updatedCategory);
        return new RedirectView("categories");
    }
    
    
    @GetMapping("/deleteCategory/{id}")
    public ModelAndView deleteCategory(@PathVariable("id") Integer categoryId){
    	Optional<Category> optCategory = categoryService.getCategoryById(categoryId);    	
    	if(optCategory.isPresent()) {
    		categoryService.deleteCategoryById(categoryId);
    	}
    	
    	return new ModelAndView("redirect:/categories");
    }
    
}
