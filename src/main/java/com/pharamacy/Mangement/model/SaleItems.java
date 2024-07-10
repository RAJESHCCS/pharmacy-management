package com.pharamacy.Mangement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@Setter
@Getter
@Table(name = "salesItems")
public class SaleItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_item_id")
    private Long saleItemId;

    @Column(name = "sale_id")
    private Long saleId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "created_at")
    private Date createdAt;
}

    // Getters and Setters











