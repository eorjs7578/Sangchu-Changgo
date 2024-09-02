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
    public UserCreateDto signUp(UserCreateDto userCreateDto) {
    //    Optional<User> existingUser = userRepository.findById(userCreateDto.getUserId())
//        if (existingUser.isPresent()) {
//            throw new IllegalArgumentException("User with this ID already exists.");
//        }

        User user = User.builder()
                .username(userCreateDto.getUsername())
                .password(userCreateDto.getPassword())
                .build();

        user = userRepository.save(user);

        return UserCreateDto.builder()
                .username(userCreateDto.getUsername())
                .password(userCreateDto.getPassword())
                .build();
    }

    @Override
    public String login(UserDto userDto) {
        User user = userRepository.findById(userDto.getUserId())
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

    @Override
    public User getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        return user;
    }
}
