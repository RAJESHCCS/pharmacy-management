package com.pharamacy.Mangement.model;


import java.time.LocalDate;

public class BatchRequestDTO {

    private int qty;
    private LocalDate expiryDate;

    public BatchRequestDTO() {
    }

    public BatchRequestDTO( int qty) {

        this.qty = qty;

    }



    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }


}