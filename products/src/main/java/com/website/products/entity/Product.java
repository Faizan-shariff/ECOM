package com.website.products.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long productId;
    
    @Column(name="product_name")
    private String productName;
    
    @Column(name="product_description")
    private String productDescription;
    
    @ElementCollection
    @Column(name="product_image_url")
    private List<String> productImageUrl;
    
    @Column(name="price")
    private double price;

}
