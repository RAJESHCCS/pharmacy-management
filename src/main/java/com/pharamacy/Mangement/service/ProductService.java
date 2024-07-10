package com.pharamacy.Mangement.service;


import com.pharamacy.Mangement.Exception.InvalidInputException;
import com.pharamacy.Mangement.Exception.InvalidOrderException;
import com.pharamacy.Mangement.Exception.ProductNotFoundException;
import com.pharamacy.Mangement.model.*;
import com.pharamacy.Mangement.repository.BatchRepository;
import com.pharamacy.Mangement.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product saveProduct(@RequestBody Product product) throws InvalidInputException {
        if(productRepository.existsByName(product.getName())){
            throw new InvalidInputException("Product with this name Already  exist");
        }

        if(product.getName()==null||product.getName().isEmpty()){
            throw new InvalidInputException("Product name cannot be blank or null");
        }
        if(product.getPrice()<=0){
            throw new InvalidInputException("Product price must be greater than 0");
        }


               Product p1 = new Product();
               p1.setName(product.getName());
               p1.setQty(0);
               p1.setPrice(product.getPrice());
               p1.setCreatedAt(LocalDate.now());
               p1.setUpdatedAt(LocalDate.now());
               Product saveProduct=productRepository.save(p1);
               return saveProduct;

        }



    public Product getByName(String  name){
        Product product=productRepository.findByName(name);
        if(product!=null){
            return product;
        }
        return null;
    }


    public List<Product> productDetails() {
        List<Product> productList = productRepository.findAll();
        return productList;
    }

    public Optional<Product> getProductById(@RequestParam Long productId) {

            Optional<Product> product = productRepository.findById(productId);

            if(product.isPresent()){
                return product;
            }
            return Optional.empty();

    }

    public Optional<Product> findById(Long id) {
        Optional<Product> productOptional=productRepository.findById(id);
        if(productOptional.isPresent()){
        return productOptional;
        }else {
            return Optional.empty();
        }
    }

    public Product updateProduct(Long productId, Product product) throws ProductNotFoundException, InvalidInputException {
        Optional<Product> existingP=productRepository.findById(productId);
        if(!existingP.isPresent()){
            throw new ProductNotFoundException("Product not found with this Id:"+product);
        }
        if(product.getName()==null||product.getName().isEmpty()){
            throw new InvalidInputException("Product name cannot be blank or null");
        }
        if(product.getPrice()<=0){
            throw new InvalidInputException("Product price must be greater than 0");
        }
        if(productRepository.existsByName(product.getName())){
            throw new InvalidInputException("Product with this name Already  exist");
        }
        Product product1=existingP.get();
        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        product1.setUpdatedAt(LocalDate.now());
        return productRepository.save(product1);
    }

    public Boolean deleteById(Long productId) {
        if(productRepository.existsById(productId)){
            List<Batch> batches = batchRepository.findByProductId(productId);
            batchRepository.deleteAll(batches);
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }

//    public ProductStockDetails getProductStockDetails(Long productId) {
//
//    }
public ProductStockDetails getProductStockDetails(Long productId) throws ProductNotFoundException {
    Optional<Product> optionalProduct = productRepository.findById(productId);

    if (optionalProduct.isEmpty()) {
        throw new ProductNotFoundException("Product does not exist with id: " + productId);
    }

    Product product = optionalProduct.get();
    List<Batch> batches = batchRepository.findByProductId(productId);

    int totalQuantity = batches.stream().mapToInt(Batch::getQty).sum();
    String alertMessage = totalQuantity >= 10 ? "Enough stock" : "Add stock";

    return ProductStockDetails.builder()
            .productId(productId)
            .productName(product.getName())
            .totalQuantity(totalQuantity)
            .batches(batches)
            .alertMessage(alertMessage)
            .build();
}


}
