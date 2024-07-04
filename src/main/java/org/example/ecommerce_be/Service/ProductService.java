package org.example.ecommerce_be.Service;

import org.example.ecommerce_be.CustomException.ProductNotFoundException;
import org.example.ecommerce_be.Entity.Product;
import org.example.ecommerce_be.Repository.ProductRepository;
import org.example.ecommerce_be.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getAllProductsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return product;
        }
        else {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

    }



    public ResponseEntity<? extends Object> searchProductsByKeyword(String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findByKeyword(keyword, pageable);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("products", products);

        // Assuming that the first product's category is used to find related products
        if (!products.isEmpty()) {
            Product firstProduct = products.getContent().get(0);
            List<Product> relatedProducts = getRelatedProducts(firstProduct.getCategory_id(), firstProduct.getProduct_id(), 5);
            response.put("relatedProducts", relatedProducts);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    public List<Product> getRelatedProducts(Long categoryId, Long excludeProductId, int limit) {
        List<Product> relatedProducts = productRepository.findByCategoryId(categoryId);
        return relatedProducts.stream()
                .filter(product -> !product.getProduct_id().equals(excludeProductId)) //product itself is not included in the list of related products.
                .limit(limit)
                .collect(Collectors.toList());
    }
}
