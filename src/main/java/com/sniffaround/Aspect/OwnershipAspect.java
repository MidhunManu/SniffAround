package com.sniffaround.Aspect;

import com.sniffaround.Exception.UserNotFoundException;
import com.sniffaround.Model.User;
import com.sniffaround.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
@AllArgsConstructor
public class OwnershipAspect {
    private final UserRepository userRepository;

    @Before("@annotation(com.sniffaround.Annotation.CheckOwnership)")
    public void checkUserOwnership(JoinPoint joinPoint) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User currentUser = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Object[] args = joinPoint.getArgs();
        Long resourceId = null;
        for(Object arg: args) {
            if (arg instanceof Long) {
                resourceId = (Long) arg;
                break;
            }
        }

        if (!currentUser.getId().equals(resourceId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
    }
}
