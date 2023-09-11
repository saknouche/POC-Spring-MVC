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
import com.sadev.app.model.Product;
import com.sadev.app.repository.ProductRepository;
import com.sadev.app.service.CategoryService;
import com.sadev.app.service.ProductService;



@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductRepository productRepository;
	
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

    @GetMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable("id") Integer id,  Model model){
    	Iterable<Category> categories = categoryService.getAllCategories();
    	model.addAttribute("categories", categories);
    	
    	Optional<Product> optProduct = productService.getProductById(id);
    	Product product = new Product();
    	
    	if(optProduct.isPresent()) {
    		product = optProduct.get();
    	}
    	model.addAttribute("product", product);
    	
        return "updateProduct";
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
    
    @PostMapping("/updateProduct")
    public RedirectView update(
    						@RequestParam("id") String productId,
    						@RequestParam("name") String name,
                            @RequestParam("description") String description,
                            @RequestParam("price") String price,
                            @RequestParam("category") String categoryId
                            
    ){

    	Integer productIdInteger = null;
    	if(productId != null) {
    		productIdInteger = Integer.parseInt(productId);
    	}
    	
    	Product updatedProduct = new Product();
    	
    	Optional<Product> optProduct = productService.getProductById(productIdInteger);
    	if(optProduct.isPresent()) {
    		updatedProduct = optProduct.get();
    	}
    	
    	Integer id = null;
    	if(categoryId != null) {
    		id = Integer.parseInt(categoryId);
    	}
    	if(name != null) {
    		updatedProduct.setName(name);
    	}
    	if(description != null) {
    		updatedProduct.setDescription(description);
    	}
    	Double priceDouble = null;
    	if(price != null) {
    		priceDouble = Double.parseDouble(price);
    		updatedProduct.setPrice(priceDouble);
    	}
    
    	Optional<Category> optCategory = categoryService.getCategoryById(id);
    	Category category = new Category();
    	if(optCategory.isPresent()) {
    		category = optCategory.get();
    		updatedProduct.setCategory(category);
    	}	
    	
        productRepository.save(updatedProduct);
        return new RedirectView("products");
    }
    
    
    @GetMapping("/deleteProduct/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") Integer productId){
    	Optional<Product> optProduct = productService.getProductById(productId);    	
    	if(optProduct.isPresent()) {
    		productService.deleteProductById(productId);
    	}
    	
    	return new ModelAndView("redirect:/products");
    }
    
}
