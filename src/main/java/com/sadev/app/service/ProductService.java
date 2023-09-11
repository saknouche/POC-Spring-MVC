package com.sadev.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sadev.app.model.Category;
import com.sadev.app.model.Product;
import com.sadev.app.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public Iterable<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}
	
	public List<Product> getAllProductsByCategory(Category category){
		return productRepository.findByCategory(category);
	}
	
	public Product createProduct(Product prodcut) {
		if(prodcut != null) {			
			Product savedProduct = productRepository.save(prodcut);
			return savedProduct;
		}
		return null;
	}
	
	public void deleteProductById(Long id) {
		if(id != null) {
			productRepository.deleteById(id);
		}
	}
}
