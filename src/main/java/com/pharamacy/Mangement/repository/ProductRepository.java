package com.pharamacy.Mangement.repository;





import com.pharamacy.Mangement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    Product findByName(String name);

    // Add custom query methods if needed
}
