package com.Auton.gibg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "user_id")
 private Long userId;

 @Column(name = "first_name", nullable = false)
 private String firstName;

 @Column(name = "last_name", nullable = false)
 private String lastName;

 @Column(name = "phone")
 private Integer phone;

 @Column(name = "profile_image")
 private String profileImage;

 @Column(name = "email", unique = true)
 private String email;

 @Column(name = "password", nullable = false)
 private String password;

 @Column(name = "role_id")
 private Integer roleId;

 @Column(name = "address_id")
 private String addressId;

 @Column(name = "FUID")
 private String fuid;

 @Column(name = "createby")
 private String createBy;

 @Column(name = "shope_id")
 private String shopeId;

 @Column(name = "point")
 private String point;

 @Column(name = "createat")
 private String createAt;


    public User(String message) {
        this.firstName = message;
    }
}
