package com.foodbox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.foodbox.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	@Query("Select p FROM Product p WHERE p.avail='yes' ORDER BY 'category'")
	List<Product> findIfAvail();
	
	@Query("SELECT p FROM Product p WHERE (p.avail LIKE 'yes') AND (p.name LIKE %?1%"
			+" OR p.des LIKE %?1%"
			+" OR p.price LIKE %?1%"
			+" OR p.category LIKE %?1%)")
	public List<Product> homeSearch(String keyword);
	
	@Query("SELECT p FROM Product p WHERE p.category LIKE 'Starters' AND p.avail LIKE 'yes'")
	public List<Product> getStarters();
	
	@Query("SELECT p FROM Product p WHERE p.category LIKE 'MainCourse' AND p.avail LIKE 'yes'")
	public List<Product> getMainCourse();
	
	@Query("SELECT p FROM Product p WHERE p.category LIKE 'Beverages' AND p.avail LIKE 'yes'")
	public List<Product> getBeverages();
	
	@Query("SELECT p FROM Product p WHERE p.category LIKE 'Desserts' AND p.avail LIKE 'yes'")
	public List<Product> getDesserts();
}
