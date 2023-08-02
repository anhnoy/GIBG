package com.Auton.gibg.response;

import com.Auton.gibg.entity.User;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String message;
    private User user;

}

