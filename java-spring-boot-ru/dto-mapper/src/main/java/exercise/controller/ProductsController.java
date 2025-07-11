package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

import exercise.repository.ProductRepository;
import exercise.dto.ProductDTO;
import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @Autowired ProductMapper productMapper;

    //GET /products – вывод списка всех товаров
    //GET /products/{id} – просмотр конкретного товара
    //POST /products – создание нового товара
    //PUT /products/{id} – редактирование товара

    @GetMapping
    public List<ProductDTO> getProducts() {
        return productRepository.findAll().stream().map(productMapper::map).toList();
    }

    @GetMapping(path = "/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productRepository.findById(id).stream().map(productMapper::map).findFirst().get();
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody ProductCreateDTO dto) {
        var product = productMapper.createProduct(dto);
        productRepository.save(product);

        return productMapper.map(product);
    }

    @PutMapping(path = "/{id}")
    public ProductDTO update(@PathVariable Long id, @RequestBody ProductUpdateDTO dto) {
        var product = productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        productMapper.update(dto, product);
        productRepository.save(product);

        return productMapper.map(product);
    }
    // END
}
