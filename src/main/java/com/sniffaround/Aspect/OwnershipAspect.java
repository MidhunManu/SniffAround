package com.sniffaround.Aspect;

import com.sniffaround.Annotation.CheckOwnership;
import com.sniffaround.Enum.ResourceTypeEnum;
import com.sniffaround.Exception.ForbiddenResourceException;
import com.sniffaround.Exception.UserNotFoundException;
import com.sniffaround.Model.User;
import com.sniffaround.Repository.PetRepository;
import com.sniffaround.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Aspect
@Component
@AllArgsConstructor
public class OwnershipAspect {
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    @Before("@annotation(checkOwnership)")
    public void checkUserOwnership(JoinPoint joinPoint, CheckOwnership checkOwnership) {
        ResourceTypeEnum resourceType = checkOwnership.value();

        String username = Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();

        User currentUser = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Object[] args = joinPoint.getArgs();
        Long resourceId = null;
        for (Object arg : args) {
            if (arg instanceof Long) {
                resourceId = (Long) arg;
                break;
            }
        }

        if (resourceId == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT);
        }

        final long id = resourceId;

        switch (resourceType) {
            case ResourceTypeEnum.USER:
                if (!currentUser.getId().equals(id))
                    throw new ForbiddenResourceException();
                break;

            case ResourceTypeEnum.PET:
                this.petRepository
                        .findByIdAndUser_Id(resourceId, currentUser.getId())
                        .orElseThrow(ForbiddenResourceException::new);
                break;
        }
    }
}
