package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.User;
import ir.maktab.homeservice.dto.MyUserDetails;
import ir.maktab.homeservice.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MyUserDetailServiceTest {

    private UserService userService;
    private MyUserDetailService myUserDetailService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        myUserDetailService = new MyUserDetailService(userService);
    }

    @Test
    void testLoadUserByUsername_shouldReturnMyUserDetails_whenUserExists() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("12345");
        when(userService.findByEmail(email)).thenReturn(user);

        UserDetails result = myUserDetailService.loadUserByUsername(email);

        assertNotNull(result);
        assertTrue(result instanceof MyUserDetails);
        assertEquals(email, result.getUsername());
        assertEquals("12345", result.getPassword());

        verify(userService, times(1)).findByEmail(email);
    }

    @Test
    void testLoadUserByUsername_shouldThrowException_whenUserNotFound() {
        String email = "notfound@example.com";
        when(userService.findByEmail(email))
                .thenThrow(new NotFoundException("User Not Found"));

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> myUserDetailService.loadUserByUsername(email)
        );

        assertEquals("User Not Found", exception.getMessage());
        verify(userService, times(1)).findByEmail(email);
    }
}
