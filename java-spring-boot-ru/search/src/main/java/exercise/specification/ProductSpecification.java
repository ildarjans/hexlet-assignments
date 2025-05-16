package exercise.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

// BEGIN
@Component
public class ProductSpecification {
    public Specification<Product> build(ProductParamsDTO params) {
        return Specification.where(withTitleCont(params.getTitleCont())
            .and(withRatingGt(params.getRatingGt()))
            .and(withCategoryId(params.getCategoryId()))
            .and(withPriceGreaterThan(params.getPriceGt()))
            .and(withPriceLessThan(params.getPriceLt())));
    }

    private Specification<Product> withTitleCont(String titleCont) {
        return ((root, query, cb) -> {
            return titleCont == null ? cb.conjunction() : cb.like(root.get("title"), "%" + titleCont.toLowerCase() + "%");
        });
    }

    private Specification<Product> withRatingGt(Double rating) {
        return ((root, query, cb) -> {
            return rating == null ? cb.conjunction() : cb.greaterThan(root.get("rating"), rating);
        });
    }

    private Specification<Product> withCategoryId(Long categoryId) {
        return ((root, query, cb) -> {
            return categoryId == null ? cb.conjunction() : cb.equal(root.get("category").get("id"), categoryId);
        });
    }

    private Specification<Product> withPriceLessThan(Integer price) {
        return ((root, query, cb) -> {
            return price == null ? cb.conjunction() : cb.lessThan(root.get("price"), price);
        });
    }
    private Specification<Product> withPriceGreaterThan(Integer price) {
        return ((root, query, cb) -> {
            return price == null ? cb.conjunction() : cb.greaterThan(root.get("price"), price);
        });
    }
}
// END
