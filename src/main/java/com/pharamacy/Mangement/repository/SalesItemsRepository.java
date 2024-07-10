package com.pharamacy.Mangement.repository;



import com.pharamacy.Mangement.model.SaleItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesItemsRepository extends JpaRepository<SaleItems, Long> {
    List<SaleItems> findBySaleId(Long saleId);

}
