package ua.finalproject.onlineshop.dto;

import lombok.*;
import ua.finalproject.onlineshop.entity.User;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UsersDTO {
private List<User> users;
}
