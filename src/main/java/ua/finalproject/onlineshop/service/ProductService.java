package ua.finalproject.onlineshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.finalproject.onlineshop.repository.CategoryRepository;
import ua.finalproject.onlineshop.repository.ProductRepository;
import ua.finalproject.onlineshop.dto.ProductDTO;
import ua.finalproject.onlineshop.entity.Category;
import ua.finalproject.onlineshop.entity.Product;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(new Product());
    }

    @Transactional
    public Product saveNewProduct(ProductDTO productDTO) throws IllegalArgumentException{
        //TODO refactor
        Category category = getCategory(productDTO);
        try {
            return productRepository.save(Product.builder()
                    .name(productDTO.getName())
                    .image(productDTO.getImage())
                    .brand(productDTO.getBrand())
                    .category(category)
                    .description(productDTO.getDescription())
                    .enabled(false)
                    .price(productDTO.getPrice())
                    .build());
        } catch (IllegalArgumentException ex){
            throw new IllegalArgumentException("Product hasn't been added");
        }
    }
    @Transactional
    public Product updateProduct(ProductDTO productDTO) throws IllegalArgumentException{
        //TODO refactor
        Product product = getProductById(productDTO.getId());
        product.setName(productDTO.getName());
        product.setImage(productDTO.getImage());
        product.setBrand(productDTO.getBrand());
        product.setDescription(productDTO.getDescription());
        product.setEnabled(false);
        product.setPrice(productDTO.getPrice());
        Category category = getCategory(productDTO);
        product.setCategory(category);
        try {
            return productRepository.save(product);
        } catch (IllegalArgumentException ex){
            throw new IllegalArgumentException("Product hasn't been added");
        }
    }

    private Category getCategory(ProductDTO productDTO) {
        return categoryRepository
                .findByName(productDTO.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("There is no such category:" +
                        productDTO.getCategory()));
    }

    public List<Product> findProductsByCategory(Long id) {
        return Optional.of(productRepository.findByCategory_Id(id)).orElse(new ArrayList<>());
    }
}
