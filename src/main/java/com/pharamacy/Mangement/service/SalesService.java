package com.pharamacy.Mangement.service;

import com.pharamacy.Mangement.Exception.InvalidOrderException;
import com.pharamacy.Mangement.Exception.ProductNotFoundException;
import com.pharamacy.Mangement.model.*;
import com.pharamacy.Mangement.repository.SalesItemsRepository;
import com.pharamacy.Mangement.repository.SalesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional

public class SalesService {

    @Autowired
    private ProductService productService;
    @Autowired
    private BatchService batchService;
    @Autowired
    private Product product;
    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private SalesItemsRepository salesItemsRepository;

    public SaleItems processOrderService(List<SalesItemDto> saleItems) throws ProductNotFoundException {
        BigDecimal totalAmount=BigDecimal.ZERO;
         List<SaleItems>productListExist=new ArrayList<>();
        //for checking existing productId or not
        for(SalesItemDto saleItem:saleItems){
            Optional<Product> products= productService.getProductById(saleItem.getProductId());
            if(products.isEmpty()){
                throw new ProductNotFoundException("Product does not exist in stock with given id:"+product.getProductId());
            }

            List<Batch> batches = batchService.findByProductIdOrderByExpiryDate(saleItem.getProductId());
            int quantityToD=saleItem.getQuantity();
            BigDecimal unitPrice= BigDecimal.valueOf(products.get().getPrice());
            BigDecimal totalItemAmount=unitPrice.multiply(BigDecimal.valueOf(quantityToD));
           totalAmount=totalAmount.add(totalItemAmount);
           for(Batch batch:batches){
               if(quantityToD<=0)
                   break;

               if(batch.getQty()<=quantityToD){
                   quantityToD-=batch.getQty();
                   batch.setQty(0);
               }else {
                   batch.setQty(batch.getQty()-quantityToD);
                   quantityToD=0;
               }
               batchService.updateBatchU(batch.getProduct().getId(),batch);
           }
          if(quantityToD>0){
              throw new ProductNotFoundException("Insufficient stock for product"+products.get().getName());
          }
          Sales sale1=new Sales();
          sale1.setSaleDate(Date.valueOf(LocalDate.now()));
          sale1.setTotalAmount(totalItemAmount);
          sale1.setCreatedAt(products.get().getCreatedAt().atStartOfDay());
          Sales saveSale=salesRepository.save(sale1);

        }
        SaleItems saleItems1=new SaleItems();
        saleItems1.getSaleId();
        return null;
    }

}
//
