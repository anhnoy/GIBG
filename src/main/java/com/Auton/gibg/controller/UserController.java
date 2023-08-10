package com.Auton.gibg.controller;


import com.Auton.gibg.entity.User;
import com.Auton.gibg.repository.UserRepository;
import com.Auton.gibg.response.*;
import com.Auton.gibg.role.Role;
import com.Auton.gibg.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserListResponse> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok().body(new UserListResponse(users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserListResponse(Collections.emptyList()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/admin")
    // Validate Admin
    public ResponseEntity<UserResponse> createAdmin(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponse("Failed to save ADMIN: " + result.getAllErrors().get(0).getDefaultMessage(), null));
        }
        // ตวจสอบ email ช้ำกันหรือไม่ ถ้าช้ำคือไม่สามาตบันทืกลงไปได้
        if (userService.isEmailExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponse("Failed to save ADMIN: Email already exists", null));
        }

        try {
            user.setRoleId(Role.ADMIN.getRoleId()); // กำหนดค่า role เป็น Role.ADMIN สำหรับผู้ใช้ที่สร้างใหม่
            User savedUser = userService.createUser(user);
            // กำหนดกานแสดงให้ส่งค่ากับไปหา client
            UserDto userDto = new UserDto();
            userDto.setUserId(savedUser.getUserId());
            userDto.setFirstName(savedUser.getFirstName());
            userDto.setLastName(savedUser.getLastName());
            userDto.setPhone(savedUser.getPhone());
            userDto.setEmail(savedUser.getEmail());
            userDto.setPassword(savedUser.getPassword());
            userDto.setRoleId(savedUser.getRoleId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new UserResponse("ADMIN saved successfully", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserResponse("Failed to save ADMIN: " + e.getMessage(), null));
        }
    }

    @PostMapping("/employee")
    // Validate Employee
    public ResponseEntity<UserResponse> createEmployee(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponse("Failed to save EMPLOYEE: " + result.getAllErrors().get(0).getDefaultMessage(), null));
        }
        // ตวจสอบ email ช้ำกันหรือไม่ ถ้าช้ำคือไม่สามาตบันทืกลงไปได้
        if (userService.isEmailExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponse("Failed to save ADMIN: Email already exists", null));
        }

        try {
            user.setRoleId(Role.EMPLOYEE.getRoleId()); // กำหนดค่า role เป็น Role.EMPLOYEE สำหรับผู้ใช้ที่สร้างใหม่
            User savedUser = userService.createUser(user);
            // กำหนดกานแสดงให้ส่งค่ากับไปหา client
            UserDto userDto = new UserDto();
            userDto.setUserId(savedUser.getUserId());
            userDto.setFirstName(savedUser.getFirstName());
            userDto.setLastName(savedUser.getLastName());
            userDto.setPhone(savedUser.getPhone());
            userDto.setEmail(savedUser.getEmail());
            userDto.setPassword(savedUser.getPassword());
            userDto.setRoleId(savedUser.getRoleId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new UserResponse("EMPLOYEE saved successfully", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserResponse("Failed to save EMPLOYEE: " + e.getMessage(), null));
        }
    }

    @PostMapping("/customer")
    // Validate Customer
    public ResponseEntity<UserResponse> createCustomer(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponse("Failed to save CUSTOMER: " + result.getAllErrors().get(0).getDefaultMessage(), null));
        }
        // ตวจสอบ email ช้ำกันหรือไม่ ถ้าช้ำคือไม่สามาตบันทืกลงไปได้
        if (userService.isEmailExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponse("Failed to save ADMIN: Email already exists", null));
        }

        try {
            user.setRoleId(Role.CUSTOMER.getRoleId()); // กำหนดค่า role เป็น Role.CUSTOMER สำหรับผู้ใช้ที่สร้างใหม่
            User savedUser = userService.createUser(user);
// กำหนดกานแสดงให้ส่งค่ากับไปหา client
            UserDto userDto = new UserDto();
            userDto.setUserId(savedUser.getUserId());
            userDto.setFirstName(savedUser.getFirstName());
            userDto.setLastName(savedUser.getLastName());
            userDto.setPhone(savedUser.getPhone());
            userDto.setEmail(savedUser.getEmail());
            userDto.setPassword(savedUser.getPassword());
            userDto.setRoleId(savedUser.getRoleId());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new UserResponse("CUSTOMER saved successfully", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserResponse("Failed to save CUSTOMER: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            if (updatedUser == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            User errorUser = new User("Cannot update user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorUser);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the user");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.getEmail(), request.getPassword());
        if (token != null) {
            return ResponseEntity.ok().body(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

}
