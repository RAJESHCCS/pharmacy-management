package com.pharamacy.Mangement.repository;

import com.pharamacy.Mangement.model.Batch;
import com.pharamacy.Mangement.model.BatchDTO;
import com.pharamacy.Mangement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch,Long> {


    List<Batch> findAllByProduct(Product product);

    List<Batch> findByProductId(Long productId);

    List<Batch> findByProductIdOrderByExpiryDateAse(Long id);
}
