package com.d108.project.interfaces.controllers;

import com.d108.project.domain.user.UserDto;
import com.d108.project.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 엔드포인트
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.signUp(userDto);
        return ResponseEntity.ok(createdUser);
    }

    // 로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        String token = userService.login(userDto);
        return ResponseEntity.ok(token);
    }

    // 회원 탈퇴 엔드포인트
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelMembership(@PathVariable Long id) {
        userService.cancelMembership(id);
        return ResponseEntity.noContent().build();
    }
}
