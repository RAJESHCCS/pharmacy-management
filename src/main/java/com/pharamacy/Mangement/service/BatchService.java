package com.pharamacy.Mangement.service;

import com.pharamacy.Mangement.Exception.BatchNotFoundException;
import com.pharamacy.Mangement.Exception.InvalidInputException;
import com.pharamacy.Mangement.Exception.ProductNotFoundException;
import com.pharamacy.Mangement.model.*;
import com.pharamacy.Mangement.repository.BatchRepository;
import com.pharamacy.Mangement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BatchService {
    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public BatchService(BatchRepository batchRepository, ProductService productService) {
        this.batchRepository = batchRepository;
        this.productService = productService;
    }

    public Batch saveBatch(Long id, BatchDTO batchDTO) throws ProductNotFoundException, InvalidInputException {
        Optional<Product> optionalProduct = productService.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product does not exist with id: " + id);
        }

        Product product = optionalProduct.get();

        if (batchDTO.getQty() <= 0) {
            throw new InvalidInputException("Quantity should be greater than 0");
        }

        if (LocalDate.now().isAfter(batchDTO.getExpiryDate())) {
            throw new InvalidInputException("Product date has expired");
        }

//        Optional<Batch>optionalBatch=batchRepository.findById(id);
//        if (optionalBatch.get().getExpiryDate().isEqual(batchDTO.getExpiryDate())) {
//            throw new InvalidInputException("Batch already exists with the given Expiry Date");
//        }

        Batch newBatch = new Batch();
        newBatch.setProduct(product);
        newBatch.setQty(batchDTO.getQty());
        newBatch.setExpiryDate(batchDTO.getExpiryDate());
        newBatch.setCreatedAt(LocalDate.now());
        newBatch.setUpdatedAt(LocalDate.now());

        return batchRepository.save(newBatch);
    }

    public List<Batch> batchDetails(Long productId) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productService.findById(productId);
        System.out.println(optionalProduct);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product does not exist with id: " + productId);
        }
        return batchRepository.findAllByProduct(optionalProduct.get());
    }
//
//
public Optional<Batch> findBatchById(Long batchId) throws BatchNotFoundException {
    Optional<Batch> optionalBatch = batchRepository.findById(batchId);
System.out.println("this is from service"+optionalBatch);
    if (optionalBatch.isEmpty()) {
        throw new BatchNotFoundException("Batch does not exist with id: " + batchId);
    }
    return Optional.of(optionalBatch.get());
}
    public String updateBatch(Long batchId, RequestDto requestDto) throws BatchNotFoundException, InvalidInputException {
        Optional<Batch> optionalBatch = batchRepository.findById(batchId);

        if (optionalBatch.isEmpty()) {
            throw new BatchNotFoundException("Batch does not exist with id: " + batchId);
        }

        Batch batch = optionalBatch.get();

        if (LocalDate.now().isAfter(batch.getExpiryDate())) {
            Product product = batch.getProduct();
            product.setQty(product.getQty() - batch.getQty());
            productService.saveProduct(product); // Assuming productService.save() updates the product
            batchRepository.delete(batch);
            return "Batch expired. Batch deleted and product quantity updated.";
        }

        batch.setQty(requestDto.getQty());
        batch.setUpdatedAt(LocalDate.now());
        batchRepository.save(batch);

        return "Batch updated successfully.";
    }
//
//
    public void deleteBatch(Long batchId) throws BatchNotFoundException, InvalidInputException {
        Optional<Batch> optionalBatch = batchRepository.findById(batchId);

        if (optionalBatch.isEmpty()) {
            throw new BatchNotFoundException("Batch does not exist with id: " + batchId);
        }

        Batch batch = optionalBatch.get();
        Product product = batch.getProduct();
        int batchQty = batch.getQty();

        // Delete the batch
        batchRepository.delete(batch);
        product.setQty(product.getQty()-batchQty);
        productService.saveProduct(product);
    }

//
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

public List<Batch> findByProductIdOrderByExpiryDate(Long id){
    List<Batch> batches=batchRepository.findByProductIdOrderByExpiryDateAse(id);
    return batches;
}

    public void updateBatchU(Long id, Batch batch) {
    }
}

