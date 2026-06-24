package com.sniffaround.Service;

import com.sniffaround.DTO.UserCreateRequest;
import com.sniffaround.DTO.UserResponse;
import com.sniffaround.DTO.UserUpdateRequest;
import com.sniffaround.Exception.UserNotFoundException;
import com.sniffaround.Mapper.UserMapper;
import com.sniffaround.Model.User;
import com.sniffaround.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> index(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return this.userRepository.findAll(pageable)
                .stream()
                .map(this.userMapper::toUserResponse)
                .toList();
    }

    public void delete(Long id) {
        this.userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("no User with Id " + id));

        this.userRepository.deleteById(id);
    }

    public User update(Long id, UserUpdateRequest userUpdateRequest) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        this.userMapper.updateUserFromDTO(userUpdateRequest, user);

        return this.userRepository.save(user);
    }

    public UserResponse show(Long id) {
        return this.userMapper.toUserResponse(this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

    public UserResponse create(UserCreateRequest request) {
        User user = this.userMapper.toUser(request);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        User saved = this.userRepository.save(user);
        return this.userMapper.toUserResponse(saved);
    }

    public UserResponse findByName(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return this.userMapper.toUserResponse(user);
    }
}
