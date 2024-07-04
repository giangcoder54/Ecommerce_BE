package org.example.ecommerce_be.Controller.Product;

import org.apache.coyote.Response;
import org.example.ecommerce_be.Entity.Product;
import org.example.ecommerce_be.Service.CategoryService;
import org.example.ecommerce_be.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

//    @Autowired
//    private CategoryService categoryService;

    @GetMapping("/paged")
    public ResponseEntity<?> products(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "10") int size){

        try {
            Page<Product> products = productService.getAllProductsPaged(page, size);
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products.getContent(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

    @GetMapping("/product-detail/{id}")
    public ResponseEntity<?> findProductById(@PathVariable("id") Long id) {
        Optional<Product> product = productService.getProductById(id);
//        Long categoryId = product.getCategory().getId();
//        List<Product>  products = productService.getRelatedProducts(categoryId);
        return new ResponseEntity<>(product, HttpStatus.OK);

    }
    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.searchProductsByKeyword(keyword, page, size);
    }


}
