package com.website.products.controller;

//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.website.products.entity.Product;
import com.website.products.service.ProductService;

import java.util.ArrayList;
import java.util.List;


//@Tag(
//        name = "CRUD Rest Apis for Product Resource",
//        description = "CRUD Rest Apis - Add Product, Add Products, List Products, Get Product By ID, Update Product, Delete Product, Get Products For Wishlist."
//)

@CrossOrigin
@RequestMapping("/api/products")
@RestController
@AllArgsConstructor
public class ProductController {

    @Autowired
    private ProductService service;

    private static final Logger LOGGER= LoggerFactory.getLogger(ProductController.class);

    /**
     * Add a single product
     * @param product
     * @return saved product
     */
//    @Operation(
//            summary = "Add Product REST API",
//            description = "Add Product REST API is used to save a new product in database"
//    )
//    @ApiResponse(
//            responseCode = "201",
//            description = "HTTP Status 201 CREATED"
//    )
    @PostMapping("/saveProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){

        LOGGER.info("POST /api/products/addProduct adding product with id:" + product.getProductId());

        Product savedProduct = service.saveProduct(product);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    /**
     * Add multiple products
     * @param products
     * @return saved products
     */
//    @Operation(
//            summary = "Add Products REST API",
//            description = "Add Products REST API is used to save a multiple new products in database"
//    )
//    @ApiResponse(
//            responseCode = "201",
//            description = "HTTP Status 201 CREATED"
//    )
    @PostMapping("/addProducts")
    public ResponseEntity<List<Product>> addProducts(@RequestBody List<Product> products){

        LOGGER.info("POST /api/products/addProducts adding list of products");

        List<Product> savedProducts = service.saveProducts(products);

        return new ResponseEntity<>(savedProducts, HttpStatus.CREATED);
    }

    /**
     * Get a list of all the products
     * @return
     */
//    @Operation(
//            summary = "Get All Products REST API",
//            description = "Get All Products REST API is used to retrieve all products from the database"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "HTTP Status 200 SUCCESS"
//    )
    @GetMapping("/productList")
    public ResponseEntity<List<Product>> findAllProducts(){

        LOGGER.info("GET /api/products/productList getting list of all products");

        List<Product> allProductsList = service.getProducts();

        return new ResponseEntity<>(allProductsList, HttpStatus.OK);
    }

    /**
     * Get a product by ID
     * @param id
     * @return
     */
//    @Operation(
//            summary = "Get Product REST API",
//            description = "Get Product REST API is used to retrieve a product from the database"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "HTTP Status 200 SUCCESS"
//    )
    @GetMapping("/productById/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id){

        LOGGER.info("GET /api/products/productById/{id} getting product with id:" + id);

        Product productById = service.getProductById(id);

        return new ResponseEntity<>(productById, HttpStatus.OK);
    }
    /**
     * Get a product by Name
     * @param name
     * @return
     */
//    @Operation(
//            summary = "Get Product REST API",
//            description = "Get Product REST API is used to retrieve a product from the database"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "HTTP Status 200 SUCCESS"
//    )
    @GetMapping("/productByName/{name}")
    public ResponseEntity<List<Product>> findProductByName(@PathVariable String name){

        LOGGER.info("GET /api/products/productByName/{name} getting product with id:" + name);

        List<Product> productByName = service.getProductByName(name);

        return new ResponseEntity<>(productByName, HttpStatus.OK);
    }

    /**
     * Updating product details
     * @param product
     * @return updated product
     */
//    @Operation(
//            summary = "Update Product REST API",
//            description = "Update Product REST API is used to update a product's details in the database"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "HTTP Status 200 SUCCESS"
//    )
    
    @PutMapping("/updateProduct")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){

        LOGGER.info("PUT /api/products/updateProduct updates product with id:" + product.getProductId());

        Product updatedProduct = service.updateProduct(product);

        return new ResponseEntity<>(updatedProduct, HttpStatus.CREATED);
    }

    /**
     * Delete a product
     * @param id
     * @return
     */
//    @Operation(
//            summary = "Delete Product REST API",
//            description = "Delete Product REST API is used to delete a product in the database"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "HTTP Status 200 SUCCESS"
//    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){

        LOGGER.info("DELETE /api/products/delete/{id} deleted product with id:" + id);

        String deletedProduct = service.deleteProduct(id);

        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }

    /**
     * Get a list of products according to user wishlist
     * @param productRequest
     * @return wishlist product details
     */
//    @Operation(
//            summary = "Get Products By Id REST API",
//            description = "Get Products By Id REST API is used to retrieve specific products from the database wrt to user"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "HTTP Status 200 SUCCESS"
//    )
    @PostMapping("/productsById")
    public ResponseEntity<List<Product>> getProductsById(@RequestBody List<Long> productRequest){

        LOGGER.info("GET /api/products/productsById retrieves specific products");

        List<Product> productDetails = new ArrayList<>();

        for (int i = 0; i < productRequest.size(); i++) {
            productDetails.add(service.getProductById(productRequest.get(i)));
        }
        return new ResponseEntity<>(productDetails, HttpStatus.OK);
    }
}
