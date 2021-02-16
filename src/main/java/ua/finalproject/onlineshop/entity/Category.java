package ua.finalproject.onlineshop.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
