package com.joaomenezes.dscatalog.repositories;

import com.joaomenezes.dscatalog.entities.Product;
import com.joaomenezes.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductResporitoryTest {

    private long existingId, nonExistingId, countTotalProducts;
    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 50L;
        countTotalProducts = 25L;
    }

    @Test
    void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist(){
        Optional<Product> product = repository.findById(existingId);

        Assertions.assertTrue(product.isPresent());
    }

    @Test
    void findByIdShouldReturnNonEmptyOptionalWhenIdExist(){
        Optional<Product> product = repository.findById(nonExistingId);

        Assertions.assertTrue(product.isEmpty());
    }

    @Test
    void saveShouldPersistWhitAutoIncrementWhenIdIsNull(){
        Product product = Factory.createProduct();
        product.setId(null);

        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());

    }

    @Test
    void deleteShouldDeleteObjectWhenIdExist(){
        repository.deleteById(existingId);

        Optional<Product> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void deleteShouldThrowExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }
}
