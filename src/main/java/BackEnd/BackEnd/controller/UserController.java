package BackEnd.BackEnd.controller;


import BackEnd.BackEnd.service.TokenService;
import BackEnd.BackEnd.exception.BaseException;
import BackEnd.BackEnd.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Map<String,Object> req)throws BaseException {
        Object response = usersService.registerUser(req);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String,Object> req) throws Exception {
        Object response = usersService.loginUser(req);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/userProfile")
    public ResponseEntity<Object> userProfile() throws Exception {
        Object response = usersService.userProfile();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/editProfile")
    public ResponseEntity<Object> editProfile(@RequestBody Map<String,Object> req) throws Exception {
        Object response = usersService.editProfile(req);
        return ResponseEntity.ok(response);
    }
}
