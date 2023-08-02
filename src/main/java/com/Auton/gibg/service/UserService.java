package com.Auton.gibg.service;



import com.Auton.gibg.entity.User;
import com.Auton.gibg.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

import static javax.crypto.Cipher.SECRET_KEY;

@Service
public class UserService {

    private static final String secret = "mySecretKey";
    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid user ID: " + id));
    }
    public User createUser(User user) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String encryptedPass = bcrypt.encode(user.getPassword());
        user.setPassword(encryptedPass);
        return userRepository.save(user);

    }
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        return userRepository.save(existingUser);
    }
    public void deleteUser(Long id) {
       User user = userRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
       userRepository.delete(user);

    }
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            if (bcrypt.matches(password, user.getPassword())) {
                String token = Jwts.builder()
                        .setSubject(email)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                        .signWith(SignatureAlgorithm.HS512, "YourSecretKeyHere") // ตั้งค่า Secret key ที่นี่
                        .compact();
                return token;
            }
        }
        return null;
    }

}
