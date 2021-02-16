package ua.finalproject.onlineshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.finalproject.onlineshop.dto.ProductDTO;
import ua.finalproject.onlineshop.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //Optional<Product> findById(Long id);
    List<Product> findByCategory_Id(Long id);
    /*@Modifying
    @Query("update Product p set p.name = :product.  where u.lastLoginDate < :date")
    void updateProduct(@Param("product") ProductDTO productDTO);*/
}
