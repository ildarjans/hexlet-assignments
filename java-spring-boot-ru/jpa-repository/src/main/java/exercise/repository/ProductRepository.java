package exercise.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import exercise.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    List<Product> findByPriceGreaterThan(int price);
    List<Product> findByPriceGreaterThanEqual(int price);
    List<Product> findByPriceLessThan(int price);
    List<Product> findByPriceLessThanEqual(int price);
    List<Product> findByPriceBetween(int minPrice, int maxPrice, Sort sort);

    // END
}
