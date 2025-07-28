package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.dto.ManagerLoginRequest;
import ir.maktab.homeservice.dto.ManagerSaveRequest;
import ir.maktab.homeservice.dto.ManagerUpdateRequest;
import ir.maktab.homeservice.dto.ManagerResponse;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.ManagerMapper;
import ir.maktab.homeservice.repository.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManagerServiceImplTest {

    @Mock
    private ManagerRepository repository;

    @Mock
    private ManagerMapper managerMapper;

    @InjectMocks
    private ManagerServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerManager_success_shouldReturnResponse() {
        ManagerSaveRequest request = new ManagerSaveRequest();
        request.setFirstName("Ali");
        request.setLastName("Ahmadi");
        request.setEmail("ali@example.com");
        request.setPassword("123456");

        when(repository.existsByEmail("ali@example.com")).thenReturn(false);

        Manager manager = new Manager();
        manager.setId(1L);
        manager.setFirstName("Ali");
        manager.setLastName("Ahmadi");
        manager.setEmail("ali@example.com");
        manager.setPassword("123456");
        manager.setWallet(new Wallet(BigDecimal.ZERO, manager));

        when(repository.save(any(Manager.class))).thenReturn(manager);

        ManagerResponse response = new ManagerResponse();
        response.setId(1L);
        response.setFirstName("Ali");
        response.setLastName("Ahmadi");
        response.setEmail("ali@example.com");

        when(managerMapper.entityMapToResponse(manager)).thenReturn(response);

        ManagerResponse result = service.registerManager(request);

        assertNotNull(result);
        assertEquals("Ali", result.getFirstName());
        assertEquals("Ahmadi", result.getLastName());
        assertEquals("ali@example.com", result.getEmail());

        verify(repository).existsByEmail("ali@example.com");
        verify(repository).save(any(Manager.class));
        verify(managerMapper).entityMapToResponse(manager);
    }

    @Test
    void registerManager_shouldThrowDuplicatedException_whenEmailExists() {
        ManagerSaveRequest request = new ManagerSaveRequest();
        request.setEmail("ali@example.com");

        when(repository.existsByEmail("ali@example.com")).thenReturn(true);

        DuplicatedException exception = assertThrows(DuplicatedException.class,
                () -> service.registerManager(request));

        assertEquals("Email address already exist", exception.getMessage());
        verify(repository).existsByEmail("ali@example.com");
        verify(repository, never()).save(any());
    }

    @Test
    void updateManager_success_shouldUpdateAndReturnResponse() {
        ManagerUpdateRequest request = new ManagerUpdateRequest();
        request.setFirstName("AliUpdated");
        request.setLastName("AhmadiUpdated");
        request.setEmail("ali.updated@example.com");
        request.setPassword("newpass");

        Manager existing = new Manager();
        existing.setId(1L);
        existing.setEmail("old@example.com");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.existsByEmail("ali.updated@example.com")).thenReturn(false);
        when(repository.save(any(Manager.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ManagerResponse response = new ManagerResponse();
        response.setId(1L);
        response.setFirstName("AliUpdated");
        response.setLastName("AhmadiUpdated");
        response.setEmail("ali.updated@example.com");

        when(managerMapper.entityMapToResponse(any(Manager.class))).thenReturn(response);

        ManagerResponse result = service.updateManager(request);

        assertNotNull(result);
        assertEquals("AliUpdated", result.getFirstName());
        assertEquals("AhmadiUpdated", result.getLastName());
        assertEquals("ali.updated@example.com", result.getEmail());

        verify(repository).findById(1L);
        verify(repository).existsByEmail("ali.updated@example.com");
        verify(repository).save(existing);
        verify(managerMapper).entityMapToResponse(existing);
    }

    @Test
    void updateManager_shouldThrowNotFoundException_whenManagerNotFound() {
        ManagerUpdateRequest request = new ManagerUpdateRequest();

        when(repository.findById(99L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.updateManager(request));

        assertEquals("Manager not found", exception.getMessage());
    }

    @Test
    void updateManager_shouldThrowDuplicatedException_whenEmailExists() {
        ManagerUpdateRequest request = new ManagerUpdateRequest();
        request.setEmail("existing@example.com");

        Manager existing = new Manager();
        existing.setId(1L);
        existing.setEmail("old@example.com");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.existsByEmail("existing@example.com")).thenReturn(true);

        DuplicatedException exception = assertThrows(DuplicatedException.class,
                () -> service.updateManager(request));

        assertEquals("Email address already exist", exception.getMessage());
    }

    @Test
    void loginManager_success_shouldReturnResponse() {
        ManagerLoginRequest request = new ManagerLoginRequest();
        request.setEmail("ali@example.com");
        request.setPassword("123456");

        Manager manager = new Manager();
        manager.setId(1L);
        manager.setEmail("ali@example.com");
        manager.setPassword("123456");

        when(repository.findByEmailAndPassword("ali@example.com", "123456"))
                .thenReturn(Optional.of(manager));

        ManagerResponse response = new ManagerResponse();
        response.setId(1L);
        response.setEmail("ali@example.com");

        when(managerMapper.entityMapToResponse(manager)).thenReturn(response);

        ManagerResponse result = service.loginManager(request);

        assertNotNull(result);
        assertEquals("ali@example.com", result.getEmail());

        verify(repository).findByEmailAndPassword("ali@example.com", "123456");
        verify(managerMapper).entityMapToResponse(manager);
    }

    @Test
    void loginManager_shouldThrowNotFoundException_whenManagerNotFound() {
        ManagerLoginRequest request = new ManagerLoginRequest();
        request.setEmail("ali@example.com");
        request.setPassword("wrongpass");

        when(repository.findByEmailAndPassword("ali@example.com", "wrongpass"))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.loginManager(request));

        assertEquals("Manager Not Found", exception.getMessage());
    }
}
