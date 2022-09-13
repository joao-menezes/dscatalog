package com.joaomenezes.dscatalog.tests;

import com.joaomenezes.dscatalog.dto.ProductDTO;
import com.joaomenezes.dscatalog.entities.Category;
import com.joaomenezes.dscatalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(1L, "Phone", "Good Phone","https://img.com/img.png" ,800.0,Instant.parse("2020-01-01T00:00:00Z"));
        product.getCategories().add(createCategory());
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

    public static Category createCategory(){
        return new Category(1L, "Electronics");
    }

}
