package com.pharamacy.Mangement.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDto {
    private String name;
    private double price;
}