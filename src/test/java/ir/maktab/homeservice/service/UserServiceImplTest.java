package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.domains.User;
import ir.maktab.homeservice.dto.UserResponse;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.UserMapper;
import ir.maktab.homeservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void filterByRole_withCustomer_returnsUserResponsePage() {
        String role = "Customer";
        Pageable pageable = PageRequest.of(0, 10);

        User user = new Customer();
        user.setId(1L);
        Page<User> userPage = new PageImpl<>(Collections.singletonList(user));

        UserResponse response = new UserResponse();
        response.setId(1L);

        when(userRepository.findByRole(Customer.class, pageable)).thenReturn(userPage);
        when(userMapper.entityMapToResponse(user)).thenReturn(response);

        Page<UserResponse> result = userService.filterByRole(role, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(response, result.getContent().get(0));

        verify(userRepository).findByRole(Customer.class, pageable);
        verify(userMapper).entityMapToResponse(user);
    }

    @Test
    void filterByRole_withSpecialist_returnsUserResponsePage() {
        String role = "Specialist";
        Pageable pageable = PageRequest.of(0, 10);

        User user = new Specialist();
        user.setId(2L);
        Page<User> userPage = new PageImpl<>(Collections.singletonList(user));

        UserResponse response = new UserResponse();
        response.setId(2L);

        when(userRepository.findByRole(Specialist.class, pageable)).thenReturn(userPage);
        when(userMapper.entityMapToResponse(user)).thenReturn(response);

        Page<UserResponse> result = userService.filterByRole(role, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(response, result.getContent().get(0));

        verify(userRepository).findByRole(Specialist.class, pageable);
        verify(userMapper).entityMapToResponse(user);
    }

    @Test
    void filterByRole_withInvalidRole_throwsNotFoundException() {
        String invalidRole = "InvalidRole";
        Pageable pageable = PageRequest.of(0, 10);

        assertThrows(NotFoundException.class, () -> {
            userService.filterByRole(invalidRole, pageable);
        });

        verify(userRepository, never()).findByRole(any(), any());
        verify(userMapper, never()).entityMapToResponse(any());
    }

    @Test
    void findAllByNameFilter_returnsUserResponsePage() {
        String firstName = "Ali";
        String lastName = "Ahmadi";
        Pageable pageable = PageRequest.of(0, 10);

        User user = new User() {};
        user.setId(1L);
        Page<User> userPage = new PageImpl<>(Collections.singletonList(user));

        UserResponse response = new UserResponse();
        response.setId(1L);


        when(userRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(userPage);
        when(userMapper.entityMapToResponse(user)).thenReturn(response);

        Page<UserResponse> result = userService.findAllByNameFilter(firstName, lastName, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(response, result.getContent().get(0));

        verify(userRepository).findAll(any(Specification.class), eq(pageable));
        verify(userMapper).entityMapToResponse(user);
    }

    @Test
    void testFindByEmail_shouldReturnUser_whenUserExists() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        User result = userService.findByEmail(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testFindByEmail_shouldThrowNotFoundException_whenUserNotFound() {
        // Arrange
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> userService.findByEmail(email)
        );
        assertEquals("User Not Found", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(email);
    }
}

