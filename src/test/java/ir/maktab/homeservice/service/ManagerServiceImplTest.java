package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.ManagerMapper;
import ir.maktab.homeservice.repository.ManagerRepository;
import ir.maktab.homeservice.security.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManagerServiceImplTest {

    @InjectMocks
    private ManagerServiceImpl managerService;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private ManagerMapper managerMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityUtil securityUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerManager_shouldRegisterManager_whenEmailIsNotDuplicated() {
        ManagerSaveRequest request = new ManagerSaveRequest();
        request.setFirstName("Ali");
        request.setLastName("Ahmadi");
        request.setEmail("ali@example.com");
        request.setPassword("123");

        when(managerRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);
        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("encoded123");

        Manager savedManager = new Manager();
        savedManager.setFirstName("Ali");
        savedManager.setLastName("Ahmadi");
        savedManager.setEmail("ali@example.com");

        ManagerResponse response = new ManagerResponse();
        response.setFirstName("Ali");
        response.setLastName("Ahmadi");
        response.setEmail("ali@example.com");

        when(managerRepository.save(any(Manager.class)))
                .thenReturn(savedManager);
        when(managerMapper.entityMapToResponse(any(Manager.class)))
                .thenReturn(response);

        ManagerResponse result = managerService.registerManager(request);

        assertEquals("Ali", result.getFirstName());
        verify(managerRepository).save(any(Manager.class));
    }

    @Test
    void registerManager_shouldThrowException_whenEmailExists() {
        ManagerSaveRequest request = new ManagerSaveRequest();
        request.setEmail("duplicate@example.com");

        when(managerRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        assertThrows(DuplicatedException.class,
                () -> managerService.registerManager(request));
    }

    @Test
    void updateManager_shouldUpdateManagerSuccessfully() {
        ManagerUpdateRequest request = new ManagerUpdateRequest();
        request.setFirstName("NewName");
        request.setLastName("NewLast");
        request.setEmail("new@example.com");
        request.setPassword("newpass");

        Manager existingManager = new Manager();
        existingManager.setEmail("old@example.com");

        when(securityUtil.getCurrentUsername()).thenReturn("old@example.com");
        when(managerRepository.findByEmail("old@example.com"))
                .thenReturn(Optional.of(existingManager));
        when(managerRepository.existsByEmail("new@example.com"))
                .thenReturn(false);
        when(passwordEncoder.encode("newpass"))
                .thenReturn("encodedNewPass");

        Manager updatedManager = new Manager();
        updatedManager.setFirstName("NewName");
        updatedManager.setLastName("NewLast");
        updatedManager.setEmail("new@example.com");

        ManagerResponse response = new ManagerResponse();
        response.setFirstName("NewName");
        response.setLastName("NewLast");

        when(managerRepository.save(any(Manager.class)))
                .thenReturn(updatedManager);
        when(managerMapper.entityMapToResponse(any(Manager.class)))
                .thenReturn(response);

        ManagerResponse result = managerService.updateManager(request);

        assertEquals("NewName", result.getFirstName());
    }

    @Test
    void updateManager_shouldThrowException_whenEmailExists() {
        ManagerUpdateRequest request = new ManagerUpdateRequest();
        request.setEmail("duplicate@example.com");

        Manager existingManager = new Manager();
        existingManager.setEmail("old@example.com");

        when(securityUtil.getCurrentUsername())
                .thenReturn("old@example.com");
        when(managerRepository.findByEmail("old@example.com"))
                .thenReturn(Optional.of(existingManager));
        when(managerRepository.existsByEmail("duplicate@example.com"))
                .thenReturn(true);

        assertThrows(DuplicatedException.class,
                () -> managerService.updateManager(request));
    }

    @Test
    void updateManager_shouldThrowNotFoundException_whenManagerNotFound() {
        String email = "manager@example.com";
        ManagerUpdateRequest request = new ManagerUpdateRequest();
        request.setEmail("new@example.com");

        when(securityUtil.getCurrentUsername()).thenReturn(email);
        when(managerRepository.findByEmail(email)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> managerService.updateManager(request));

        assertEquals("Manager Not Found", exception.getMessage());
        verify(managerRepository).findByEmail(email);
    }


    @Test
    void loginManager_shouldReturnManagerResponse_whenFound() {
        ManagerLoginRequest request = new ManagerLoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("pass");

        Manager manager = new Manager();
        manager.setEmail("test@example.com");

        ManagerResponse response = new ManagerResponse();
        response.setEmail("test@example.com");

        when(managerRepository.findByEmailAndPassword(
                "test@example.com", "pass"))
                .thenReturn(Optional.of(manager));
        when(managerMapper.entityMapToResponse(manager)).thenReturn(response);

        ManagerResponse result = managerService.loginManager(request);

        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void loginManager_shouldThrowException_whenNotFound() {
        ManagerLoginRequest request = new ManagerLoginRequest();
        request.setEmail("notfound@example.com");
        request.setPassword("pass");

        when(managerRepository.findByEmailAndPassword(
                "notfound@example.com", "pass"))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> managerService.loginManager(request));
    }
}
