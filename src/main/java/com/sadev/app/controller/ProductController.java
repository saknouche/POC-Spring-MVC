package com.sadev.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.sadev.app.model.Category;
import com.sadev.app.model.Product;
import com.sadev.app.service.CategoryService;
import com.sadev.app.service.ProductService;


@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/products")
	public String home(Model model){
		
		Iterable<Product> products =  productService.getAllProducts();
		model.addAttribute("products", products);
		
		return "home";
	}
	
    @GetMapping("/addProduct")
    public String addProduct(Model model){
    	Iterable<Category> categories = categoryService.getAllCategories();
    	model.addAttribute("categories", categories);
    	
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public RedirectView add(@RequestParam("name") String name,
                            @RequestParam("description") String description,
                            @RequestParam("price") String price,
                            @RequestParam("category") String categoryId
                            
    ){

    	Integer id = null;
    	if(categoryId != null) {
    		id = Integer.parseInt(categoryId);
    	}
    	Double priceDouble = null;
    	if(price != null) {
    		priceDouble = Double.parseDouble(price);
    	}
    	Optional<Category> optCategory = categoryService.getCategoryById(id);
    	Category category = new Category();
    	if(optCategory.isPresent()) {
    		category = optCategory.get();
    	}
        productService.createProduct(new Product(name, description, priceDouble, category));
        return new RedirectView("products");
    }
    
}
