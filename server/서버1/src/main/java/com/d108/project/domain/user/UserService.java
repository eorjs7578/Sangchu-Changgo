package com.d108.project.domain.user;

public interface UserService {
    void saveUser(User user);

    UserCreateDto signUp(UserCreateDto userCreateDto);
    String login(UserDto userDto);
    void cancelMembership(Long userId);
    User getUserById(Long userId);
}

