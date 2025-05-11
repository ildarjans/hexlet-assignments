package exercise.controller;

import java.util.List;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.mapper.ProductMapper;
import exercise.model.Category;
import exercise.model.Product;
import exercise.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN
    private Category getCategoryIfPresent(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @GetMapping
    public List<ProductDTO> getProducts() {
        return productRepository.findAll().stream().map(productMapper::map).toList();
    }

    @GetMapping(path = "/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return productMapper.map(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@Valid @RequestBody ProductCreateDTO dto) {
        var productModel = productMapper.map(dto);

        getCategoryIfPresent(dto.getCategoryId());

        var product = productRepository.save(productModel);

        return productMapper.map(product);
    }

    @PutMapping(path = "/{id}")
    public ProductDTO update(@PathVariable Long id, @RequestBody ProductUpdateDTO dto) {
        var productModel = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));


        Long dtoCategoryId = dto.getCategoryId().get();

        if (dtoCategoryId != null && !dtoCategoryId.equals(productModel.getCategory().getId())) {
            var category = getCategoryIfPresent(dtoCategoryId);

            var categoryProducts = productModel.getCategory().getProducts();
            categoryProducts.remove(productModel);
            productModel.setCategory(null);
            categoryProducts.add(productModel);
            productModel.setCategory(category);
        }

        productMapper.update(dto, productModel);

        productRepository.save(productModel);


        return productMapper.map(productModel);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
    // END
}
