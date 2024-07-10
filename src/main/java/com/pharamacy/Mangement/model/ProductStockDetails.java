package com.pharamacy.Mangement.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductStockDetails {
    private Long productId;
    private String productName;
    private int totalQuantity;
    private List<Batch> batches;
    private String alertMessage;
}
