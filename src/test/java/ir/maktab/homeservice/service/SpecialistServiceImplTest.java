package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.dto.SpecialistLoginRequest;
import ir.maktab.homeservice.dto.SpecialistResponse;
import ir.maktab.homeservice.dto.SpecialistSaveRequest;
import ir.maktab.homeservice.dto.SpecialistUpdateInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpecialistServiceImplTest {

    private SpecialistService service;
    private SpecialistResponse response;
    private SpecialistSaveRequest saveRequest;
    private SpecialistUpdateInfo updateRequest;
    private SpecialistLoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(SpecialistService.class);
        response = Mockito.mock(SpecialistResponse.class);
        saveRequest = Mockito.mock(SpecialistSaveRequest.class);
        updateRequest = Mockito.mock(SpecialistUpdateInfo.class);
        loginRequest = Mockito.mock(SpecialistLoginRequest.class);
    }

    @Test
    void registerSpecialist() {
        Mockito.when(service.registerSpecialist(saveRequest)).thenReturn(response);
        assertEquals(response, service.registerSpecialist(saveRequest));
    }

    @Test
    void loginSpecialist() {
        Mockito.when(service.loginSpecialist(loginRequest)).thenReturn(response);
        assertEquals(response, service.loginSpecialist(loginRequest));
    }

    @Test
    void updateSpecialistInfo() {
        Mockito.when(service.updateSpecialistInfo(updateRequest)).thenReturn(response);
        assertEquals(response, service.updateSpecialistInfo(updateRequest));
    }

    @Test
    void approveSpecialistRegistration() {
        Mockito.when(service.approveSpecialistRegistration(Mockito.anyLong()))
                .thenReturn(response);
        assertEquals(response, service.approveSpecialistRegistration(Mockito.anyLong()));
    }

    @Test
    void addSpecialistToHomeService() {
        Mockito.doNothing().when(service)
                .addSpecialistToHomeService(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(service, Mockito.times(0))
                .addSpecialistToHomeService(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void removeSpecialistFromHomeService() {
        Mockito.doNothing().when(service)
                .removeSpecialistFromHomeService(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(service, Mockito.times(0))
                .removeSpecialistFromHomeService(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void findAllSpecialists() {
        Mockito.when(service.findAllSpecialists()).thenReturn(List.of(response));
        assertEquals(List.of(response), service.findAllSpecialists());
    }

    @Test
    void findAllByFirstNameContainsIgnoreCaseOrderByIdAsc() {
        Mockito.when(service
                        .findAllByFirstNameContainsIgnoreCaseOrderByIdAsc
                                (Mockito.anyString()))
                .thenReturn(List.of(response));
        assertEquals(List.of(response),
                service.findAllByFirstNameContainsIgnoreCaseOrderByIdAsc
                        (Mockito.anyString()));
    }

    @Test
    void findAllByLastNameContainsIgnoreCaseOrderByIdAsc() {
        Mockito.when(service
                        .findAllByLastNameContainsIgnoreCaseOrderByIdAsc
                                (Mockito.anyString()))
                .thenReturn(List.of(response));
        assertEquals(List.of(response),
                service.findAllByLastNameContainsIgnoreCaseOrderByIdAsc
                        (Mockito.anyString()));
    }

    @Test
    void findAllByHomeServiceTitle() {
        Mockito.when(service.findAllByHomeServiceTitle(Mockito.anyString()))
                .thenReturn(List.of(response));
        assertEquals(List.of(response) ,
                service.findAllByHomeServiceTitle(Mockito.anyString()));
    }
}