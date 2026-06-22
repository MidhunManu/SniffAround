package com.sniffaround;

import com.sniffaround.Controller.UserController;
import com.sniffaround.DTO.UserResponse;
import com.sniffaround.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    void shouldReturnUsers() {

        UserService userService = mock(UserService.class);

        UserController controller =
                new UserController(userService);


        List<UserResponse> users = List.of(
                new UserResponse(
                        1L,
                        "testuser",
                        "test@test.com",
                        "avatar.png"
                )
        );


        when(userService.index(0, 1))
                .thenReturn(users);


        ResponseEntity<List<UserResponse>> response =
                controller.index(0, 1);


        assertEquals(200, response.getStatusCode().value());
        assertEquals(users, response.getBody());

        verify(userService)
                .index(0, 1);
    }
}