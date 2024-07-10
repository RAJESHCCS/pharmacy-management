package com.pharamacy.Mangement.model;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RequestDto {

    private int qty;
    private LocalDate expiryDate;
}