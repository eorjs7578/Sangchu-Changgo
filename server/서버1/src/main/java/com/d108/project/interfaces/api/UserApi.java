package com.d108.project.interfaces.api;

import com.d108.project.domain.user.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface UserApi {

    @PostMapping("/signup")
    ResponseEntity<UserDto> signUp(@RequestBody UserDto userDto);

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody UserDto userDto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> cancelMembership(@PathVariable Long id);

    // 추가적인 사용자 관련 API 메서드들
}