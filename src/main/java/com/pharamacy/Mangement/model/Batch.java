package com.pharamacy.Mangement.model;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "batch")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;

//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;
    private int qty;
    private LocalDate expiryDate;
    private LocalDate createdAt;
    private LocalDate updatedAt;




    public Batch(Long batchId, Product product, int qty, LocalDate expiryDate, LocalDate createdAt, LocalDate updatedAt) {
        this.batchId = batchId;
        this.product = product;
        this.qty = qty;
        this.expiryDate = expiryDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

//package com.pharamacy.management.spring_pharmacy.model;
//
//
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.Builder;
//import java.time.LocalDate;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Builder
//@Entity
//@Table(name = "batch")
//public class Batch {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long batchId;
//
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "productId", referencedColumnName = "id")
//    private Product product;
//
//    private int qty;
//    private LocalDate expiryDate;
//    private LocalDate createdAt;
//    private LocalDate updatedAt;
//
//    public Batch(Long batchId, Product product, int qty, LocalDate expiryDate, LocalDate createdAt, LocalDate updatedAt) {
//        this.batchId = batchId;
//        this.product = product;
//        this.qty = qty;
//        this.expiryDate = expiryDate;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//    }
//}
