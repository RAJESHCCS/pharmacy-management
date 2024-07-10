package com.pharamacy.Mangement.model;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesItemDto {
    private Long productId;
    private int quantity;
}
