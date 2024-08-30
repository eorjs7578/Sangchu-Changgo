package com.d108.project.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDto signUp(UserDto userDto) {
        Optional<User> existingUser = userRepository.findById(userDto.getId());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User with this ID already exists.");
        }

        User user = User.builder()
                .id(userDto.getId())
                .password(userDto.getPassword())
                .build();

        user = userRepository.save(user);

        return UserDto.builder()
                .id(user.getId())
                .build();
    }

    @Override
    public String login(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID or password."));

        if (!userDto.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("Invalid ID or password.");
        }

        return "로그인 성공";
    }

    @Override
    public void cancelMembership(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        userRepository.delete(user);
    }
}
