package com.sadev.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sadev.app.model.Category;
import com.sadev.app.model.Product;
import java.util.List;


@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

	List<Product> findByCategory(Category category);
}
