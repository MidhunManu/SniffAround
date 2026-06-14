package com.sniffaround.Service;

import com.sniffaround.DTO.UserCreateRequest;
import com.sniffaround.DTO.UserResponse;
import com.sniffaround.DTO.UserUpdateRequest;
import com.sniffaround.Exception.UserNotFoundException;
import com.sniffaround.Mapper.UserMapper;
import com.sniffaround.Model.User;
import com.sniffaround.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> index() {
        return this.userMapper.toUserResponseList(this.userRepository.findAll());
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
        User saved = this.userRepository.save(user);
        return this.userMapper.toUserResponse(saved);
    }
}
