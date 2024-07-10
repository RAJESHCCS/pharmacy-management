package com.pharamacy.Mangement.model;


import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int qty;
    private double price;
    private LocalDate createdAt;
    private LocalDate updatedAt;

//    @ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
//    private Batch batch;


    //Getter , Setter and Constructor
}
