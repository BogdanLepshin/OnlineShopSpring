package ua.finalproject.onlineshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.finalproject.onlineshop.repository.UserRepository;
import ua.finalproject.onlineshop.dto.RegisterForm;
import ua.finalproject.onlineshop.dto.UsersDTO;
import ua.finalproject.onlineshop.entity.RoleType;
import ua.finalproject.onlineshop.entity.User;


@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public UsersDTO getAllUsers() {
        //TODO checking for an empty user list
        return new UsersDTO(userRepository.findAll());
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void saveNewUser(RegisterForm form){
        form.setPassword(bCryptPasswordEncoder.encode(form.getPassword()));
        try {
            userRepository.save(User.builder()
                    .firstName(form.getFirstName())
                    .lastName(form.getLastName())
                    .email(form.getEmail())
                    .password(form.getPassword())
                    .role(RoleType.ROLE_USER)
                    .build());
        } catch (IllegalArgumentException ex){
            throw new IllegalArgumentException("User already exists!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("Not Found"));
    }
}
