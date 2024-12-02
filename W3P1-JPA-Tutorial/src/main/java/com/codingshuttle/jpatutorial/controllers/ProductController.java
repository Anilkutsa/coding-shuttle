package com.codingshuttle.jpatutorial.controllers;

import com.codingshuttle.jpatutorial.entities.ProductEntity;
import com.codingshuttle.jpatutorial.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {


    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /** SORTING EXAMPLE 1 - Get all items, Sorted by Price */
    @GetMapping("/all")
    public List<ProductEntity> getAllItemSortedByPrice() {
        return productRepository.findByOrderByPrice();
    }

    /** SORTING EXAMPLE 2 - Get all items, Sorted by param being passed in request param
     * Example 1 - http://localhost:8080/products/sort?sortBy=id
     * Example 2 - http://localhost:8080/products/sort?sortBy=title
     * */
    @GetMapping("/sort")
    public List<ProductEntity> getItemsSortedBy(@RequestParam(defaultValue = "id") String sortBy) {

        /* Returns all the items sorted by param */
        // return productRepository.findBy(Sort.by(sortBy));

        /* Returns all the items sorted by param along with ASC or DESC  */
        // return productRepository.findBy(Sort.by(Sort.Direction.ASC, sortBy));

        /* Returns all the items sorted by param along with ASC or DESC &
         * if two products match the criteria, sort by price */
         //return productRepository.findBy(Sort.by(Sort.Direction.ASC, sortBy, "price"));

        /* Returns all the items sorted by sortBy Param in ascending order
         * and then sorts results matching the same criteria by Price in desc order */
        return productRepository.findBy(Sort.by(Sort.Order.asc(sortBy), Sort.Order.desc("price")));
    }

    private final int PAGE_NUMBER = 0;
    private final int PAGE_SIZE = 5;

    /** PAGINATION EXAMPLE 1
     * Pass Pageable object to JPA method with page number, size and sort by (optional)
     * Returns results in Page object format with all the pagination details */
    @GetMapping("/page")
    public Page<ProductEntity> getItemsPaginated(@RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.by("id"));
        return productRepository.findAll(pageable);
    }
}
