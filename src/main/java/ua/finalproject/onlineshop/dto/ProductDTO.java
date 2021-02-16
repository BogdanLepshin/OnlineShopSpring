package ua.finalproject.onlineshop.dto;

import lombok.*;
import ua.finalproject.onlineshop.entity.Product;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDTO {
    private Long id;
    private String name;
    private String image;
    private String category;
    private String brand;
    private String description;
    private int price;

    public ProductDTO toProductDto(Product product) {
        id = product.getId();
        name = product.getName();
        image = product.getImage();
        category = product.getCategory().getName();
        brand = product.getBrand();
        description = product.getDescription();
        price = product.getPrice();
        return this;
    }
}
