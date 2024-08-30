package com.d108.project.domain.user;

public interface UserService {
    void saveUser(User user);

    UserDto signUp(UserDto userDto);
    String login(UserDto userDto);
    void cancelMembership(Long userId);
}

