package com.foodbox.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.foodbox.model.Admin;
import com.foodbox.model.Product;
import com.foodbox.repository.AdminRepository;
import com.foodbox.repository.ProductRepository;

import com.foodbox.exception.ResourceNotFoundException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired AdminRepository adminRepository;

	@GetMapping("/products/Admin")
	public List<Product> getAdminProducts() {
		return productRepository.findAll();
	}

	@GetMapping("/products/cust")
	public List<Product> getAllProducts() {
		List<Product> prodList=productRepository.findIfAvail();
		if(prodList.isEmpty()) {
			List<Admin> adminList = adminRepository.findAll();
			if(adminList.isEmpty()) {
				adminRepository.save(new Admin("admin","password"));
			}
			addProdIfEmpty(new Product(1,"Butter Chicken","Chicken infused with butter and spices","Indian",350,0,0,"yes","./assets/images/ButterChicken.png"));
			addProdIfEmpty(new Product(2,"Chicken Biryani","Rice Steamed with Chicken and spices","Indian",365,10,0,"yes","./assets/images/biryani.jpg"));
			addProdIfEmpty(new Product(3,"Steamed Mince Bun","Steamed Bun with lamb mince","Chinese",250,20,0,"yes","./assets/images/buns.jpg"));
			addProdIfEmpty(new Product(4,"Non veg wool pack","Chicken crunchy with spicy","Starters",150,5,0,"yes","./assets/images/woolpack.jpeg"));
			addProdIfEmpty(new Product(2,"Paneer Pizza","Pizza topped with cotted cheese and vegies","Italian",435,0,0,"yes","./assets/images/paneerpizza.jpg"));
			addProdIfEmpty(new Product(2,"Red Sause Pasta","Pasta with Tomato and oregano","Italian",435,0,0,"yes","./assets/images/redPasta.jpg"));
			addProdIfEmpty(new Product(2,"Ravioli","Ravioli pasta filled with veg mince","Italian",200,18,0,"yes","./assets/images/ravioli.jpg"));
			addProdIfEmpty(new Product(2,"Elote de Corn","Corn topped with cream cheese and spice","Mexican",180,7,0,"yes","./assets/images/elote.jpg"));
		    addProdIfEmpty(new Product(2,"Burrito","Wrapped Tortilla with Meat mince and Mayo","Mexican",350,0,0,"yes","./assets/images/Burrito.jpg"));
			
			addProdIfEmpty(new Product(3,"South Indian Thali","Full meals","MainCourse",199,2,0,"yes","./assets/images/thali.jpeg"));
			addProdIfEmpty(new Product(4,"Chilli Mushroom","Fried Mushroom with Chilli Sauce","Starters",120,0,0,"yes","./assets/images/mushroomchilli.jpeg"));
			addProdIfEmpty(new Product(3,"Tomato Soup","Steamed Tomato with Spices","Starters",75,0,0,"yes","./assets/images/tomato.jpeg"));
			addProdIfEmpty(new Product(3,"Chicken Lollipop","Fried Chicken ","Starters",130,2,0,"yes","./assets/images/ChickenLollipop.jpg"));
			
			addProdIfEmpty(new Product(5,"Green Tea","Healthy Tea","Bevarages",30,0,0,"yes","./assets/images/greentea.jpeg"));
			addProdIfEmpty(new Product(2,"Strawberry Juice","Juice with strawberry crush ","Bevarages",80,0,0,"yes","./assets/images/strawberry.jpeg"));
			addProdIfEmpty(new Product(5,"Faloda","Healthy Tea","Bevarages",30,0,0,"yes","./assets/images/greentea.jpeg"));
			addProdIfEmpty(new Product(4,"Cold Cofffee","Creamy thick coffee","Bevarages",85,2,0,"yes","./assets/images/coldcoffee.jpeg"));
			addProdIfEmpty(new Product(7,"Mango Juice","Fruit King Juice","Bevarages",99,3,0,"yes","./assets/images/mango.jpeg"));
			
			addProdIfEmpty(new Product(5,"Rasmalai","Sweet ","Desserts",100,0,0,"yes","./assets/images/rasmalai.jpeg"));
			addProdIfEmpty(new Product(5,"Gulab Jamun","Sweet","Desserts",50,0,0,"yes","./assets/images/jamun.jpeg"));
			addProdIfEmpty(new Product(5,"Putharekulu","Sweet ","Desserts",120,0,0,"yes","./assets/images/putharekulu.jpeg"));
			addProdIfEmpty(new Product(5,"Cheesy Cake","Sweet","Desserts",65,0,0,"yes","./assets/images/cheesy cake.jpeg"));
			
			addProdIfEmpty(new Product(5,"Cold Cake","Sweet ","Desserts",100,0,0,"yes","./assets/images/coldcake.jpeg"));
			addProdIfEmpty(new Product(1,"Chocolate cake","Sweet","Desserts",50,0,0,"yes","./assets/images/choclate.jpg"));
			
			
			
			
			
			prodList=productRepository.findIfAvail();
		}
		return prodList;
	}
	
	public void addProdIfEmpty(Product product) {
		int min = 10000;
		int max = 99999;
		int b = (int) (Math.random() * (max - min + 1) + min);
		product.setId(b);
		float temp = (product.getActualPrice()) * (product.getDiscount() / 100);
		float price = product.getActualPrice() - temp;
		product.setPrice(price);
		productRepository.save(product);
	}
	
//@PutMapping("/products")
	@PostMapping("/products")
	public Product addProduct(@RequestBody Product product) {
		int min = 10000;
		int max = 99999;
		int b = (int) (Math.random() * (max - min + 1) + min);
		product.setId(b);
		float temp = (product.getActualPrice()) * (product.getDiscount() / 100);
		float price = product.getActualPrice() - temp;
		product.setPrice(price);
		return productRepository.save(product);
	}
	
//@PostMapping("/products/{id}")
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails){
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not Found with " + id));
		product.setName(productDetails.getName());
		product.setDesc(productDetails.getDesc());
		product.setCategory(productDetails.getCategory());
		product.setImagepath(productDetails.getImagepath());
		product.setActualPrice(productDetails.getActualPrice());
		product.setDiscount(productDetails.getDiscount());
		product.setAvail(productDetails.getAvail());
		float temp = (product.getActualPrice()) * (product.getDiscount() / 100);
		float price = product.getActualPrice() - temp;
		product.setPrice(price);
		
		Product updatedProd = productRepository.save(product);
		return ResponseEntity.ok(updatedProd);
		
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not Found with " + id));
		productRepository.delete(product);
		Map<String, Boolean> map = new HashMap<>();
		map.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(map);
	}

	@GetMapping("products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product Not Found with " + id));
		return ResponseEntity.ok(product);
	}

	@GetMapping("products/search/{keyword}")
	public List<Product> getSearchProducts(@PathVariable String keyword) {
		return productRepository.homeSearch(keyword);
	}

	@GetMapping("products/Starters")
	public List<Product> getStarters() {
		return productRepository.getStarters();
	}

	@GetMapping("products/MainCourse")
	public List<Product> getMainCourse() {
		return productRepository.getMainCourse();
	}

	@GetMapping("products/Beverages")
	public List<Product> getBeverages() {
		return productRepository.getBeverages();
	}

	@GetMapping("products/Desserts")
	public List<Product> getDesserts() {
		return productRepository.getDesserts();
	}
}