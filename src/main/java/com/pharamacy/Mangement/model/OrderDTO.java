package com.pharamacy.Mangement.model;

import java.util.List;

public class OrderDTO {
    private List<SalesItemDto> saleItems;

    public OrderDTO() {
    }

    public OrderDTO(List<SalesItemDto> saleItems) {
        this.saleItems = saleItems;
    }

    public List<SalesItemDto> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<SalesItemDto> saleItems) {
        this.saleItems = saleItems;
    }
}
