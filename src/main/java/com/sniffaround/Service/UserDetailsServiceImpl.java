package com.sniffaround.Service;

import com.sniffaround.Exception.UserNotFoundException;
import com.sniffaround.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        com.sniffaround.Model.User appUser = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return User.withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(appUser.getRole())
                .build();
    }
}
