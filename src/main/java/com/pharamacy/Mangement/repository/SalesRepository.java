package com.pharamacy.Mangement.repository;

import com.pharamacy.Mangement.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
    // Add custom query methods if needed
}
