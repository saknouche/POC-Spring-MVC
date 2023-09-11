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
	
	public Optional<Product> getProductById(Integer id) {
		return productRepository.findById(id);
	}
	
	public List<Product> getAllProductsByCategory(Category category){
		return productRepository.findByCategory(category);
	}
	
	public Product createProduct(Product product) {
		if(product != null) {			
			Product savedProduct = productRepository.save(product);
			return savedProduct;
		}
		return null;
	}
	
	public void deleteProductById(Integer id) {
			productRepository.deleteById(id);
	}
}
