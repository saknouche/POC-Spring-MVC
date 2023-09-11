package com.sadev.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sadev.app.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
