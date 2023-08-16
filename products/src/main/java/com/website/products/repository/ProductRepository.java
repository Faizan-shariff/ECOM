package com.website.products.repository;

import com.website.products.entity.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {
	
	public Product findByProductId(Long productId);
	
	public List<Product> findByProductNameIgnoreCaseContaining(String productName);

}
