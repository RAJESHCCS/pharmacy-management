package com.pharamacy.Mangement.controller;

import com.pharamacy.Mangement.Exception.BatchNotFoundException;
import com.pharamacy.Mangement.Exception.InvalidInputException;
import com.pharamacy.Mangement.Exception.InvalidOrderException;
import com.pharamacy.Mangement.Exception.ProductNotFoundException;
import com.pharamacy.Mangement.model.*;
import com.pharamacy.Mangement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
public class AllController {
    @Autowired

    private ProductService productService;
    @Autowired
    private BatchService batchService;
    @Autowired
    private SalesService salesService;

    @Autowired
    public AllController(ProductService productService, BatchService batchService, SalesService salesService) {
        this.productService = productService;
        this.batchService = batchService;
        this.salesService=salesService;
    }

    //Endpoint 1
    @PostMapping("/product/add")

    public ResponseEntity<Object> addProduct(@RequestBody Product product) {
        try{
        Product response = productService.saveProduct(product);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (InvalidInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

   // Endpoint 2
    @GetMapping("/product")
    public ResponseEntity<Object> getAllProducts(@RequestParam Long product_id) {
            Optional<Product> product = productService.getProductById(product_id);
                return product.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(createResponseMessage(false, "No product list found for pharmacy")));



    }

    //Endpoint 3
    @GetMapping("/product/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        return product.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(createResponseMessage(false, "product not found")));
    }
//    private
//
//    //Endpoint 4
    @PutMapping("/product/update/{product_id}")
    public ResponseEntity<Object> updateProduct(@PathVariable("product_id") Long productId,@RequestBody Product product) {
        try {
            Product product1 = productService.updateProduct(productId, product);
            return ResponseEntity.ok(product1);
        }catch (ProductNotFoundException | InvalidInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }

//
//    //Endpoint 5
    @DeleteMapping("/product/delete/{product_id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("product_id") Long productId) {
        try {
            Boolean isDeleted = productService.deleteById(productId);
            if (isDeleted)
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product deleted successfully with id:"+productId);
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not exist with given id:"+productId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createResponseMessage(false, "product not found"));
        }
    }


    //Endpoint 6
    @PostMapping("/product/batch/add/{product_id}")
    public ResponseEntity<Object> addBatch(@PathVariable("product_id") Long productId, @RequestBody BatchDTO batch)  {

            try {
               Batch batch1= batchService.saveBatch(productId, batch);
                return ResponseEntity.status(HttpStatus.CREATED).body(batch1);
            } catch (ProductNotFoundException | InvalidInputException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
    }

    //Endpoint 7

    @GetMapping("/product/{product_id}/batches")
    public ResponseEntity<Object> getAllBatches(@PathVariable("product_id") Long productId) {
        try {
            List<Batch> allBatches = batchService.batchDetails(productId);
            if (allBatches.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No batch list found for the product");
            } else {
                return ResponseEntity.ok(allBatches);
            }
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    //Endpoint 8

    @GetMapping("/product/batchById/{batch_id}")
    public ResponseEntity<Object> getBatchById(@PathVariable("batch_id") Long batchId) {
        try {

            Optional<Batch> batch = batchService.findBatchById(batchId);
            System.out.println("this is from controller "+batch);
            return ResponseEntity.ok(batch);
        } catch (BatchNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    //Endpoint 9

    @PostMapping("/product/batch/update/{batch_id}")
    public ResponseEntity<Object> updateBatch(@PathVariable("batch_id") Long batchId, @RequestBody RequestDto requestDto) {
        try {
            String message = batchService.updateBatch(batchId,requestDto);
            return ResponseEntity.ok(message);
        } catch (BatchNotFoundException | InvalidInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //Endpoint 10

    @PostMapping("/product/batch/delete/{batch_id}")
    public ResponseEntity<Object> deleteBatch(@PathVariable("batch_id") Long batchId) {
        try {
            batchService.deleteBatch(batchId);
            return ResponseEntity.ok(createResponseMessage(true, "Batch deleted successfully and product quantity updated."));
        } catch (BatchNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
    }

    //Endpoint 11
//    @GetMapping("/product/stock/{product_id}")
//    public ResponseEntity<Object> getProductStockDetails(@PathVariable("product_id") Long productId) {
//
//        return ResponseEntity.ok(createResponseMessage(true, "Placeholder for logic of displaying stock of specific product"));
//
//    }
    @GetMapping("/product/stock/{product_id}")
    public ResponseEntity<Object> getProductStockDetails(@PathVariable("product_id") Long productId)  {
        try {
            ProductStockDetails productStockDetails = productService.getProductStockDetails(productId);
            return ResponseEntity.ok(productStockDetails);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    //Endpoint 12
    @PostMapping("/processOrder")
    public ResponseEntity<Object> processOrder(List<SalesItemDto> saleItems) {


        return ResponseEntity.ok(createResponseMessage(true, "Placeholde for logic of processing order "));

    }

    //Endpoint 13
    @GetMapping("/allSales")
    public ResponseEntity<Object> getAllSales() {
        return ResponseEntity.ok(createResponseMessage(true, "Placeholde for logic of retrival of all sales from sales table"));

    }

    //Endpoint 14
    @GetMapping("/sales/{saleId}/items")
    public ResponseEntity<Object> getSaleItemsBySaleId(@PathVariable Long saleId) {
        return ResponseEntity.ok(createResponseMessage(true, "Placeholde for logic of retrival of all sales items  for specific sales id"));

    }





    private Map<String, Object> createResponseMessage(Boolean status, Object message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", status);
        response.put("message", message);
        return response;
    }
}









