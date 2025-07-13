package ir.maktab.homeservice.service;

import ir.maktab.homeservice.dto.ManagerLoginRequest;
import ir.maktab.homeservice.dto.ManagerResponse;
import ir.maktab.homeservice.dto.ManagerSaveRequest;
import ir.maktab.homeservice.dto.ManagerUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ManagerServiceImplTest {

    private ManagerService service;
    private ManagerResponse response;
    private ManagerSaveRequest saveRequest;
    private ManagerUpdateRequest updateRequest;
    private ManagerLoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(ManagerService.class);
        response = Mockito.mock(ManagerResponse.class);
        saveRequest = Mockito.mock(ManagerSaveRequest.class);
        updateRequest = Mockito.mock(ManagerUpdateRequest.class);
        loginRequest = Mockito.mock(ManagerLoginRequest.class);
    }

    @Test
    void registerManager() {
        Mockito.when(service.registerManager(saveRequest)).thenReturn(response);
        assertEquals(response, service.registerManager(saveRequest));
    }

    @Test
    void updateManager() {
        Mockito.when(service.updateManager(updateRequest)).thenReturn(response);
        assertEquals(response, service.updateManager(updateRequest));
    }

    @Test
    void loginManager() {
        Mockito.when(service.loginManager(loginRequest)).thenReturn(response);
        assertEquals(response, service.loginManager(loginRequest));
    }
}