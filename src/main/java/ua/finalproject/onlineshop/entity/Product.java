package ua.finalproject.onlineshop.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String image;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String brand;
    @Column(nullable = false, length = 512)
    private String description;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private boolean enabled;

    public String getProductImagePath() {
        return "/product_images/" + id + "/" + image;
    }
}
