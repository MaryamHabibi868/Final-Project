/*
package ir.maktab.homeservice.service;

import ir.maktab.homeservice.dto.*;
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
    private HomeServiceResponse homeServiceResponse;
    private TransactionResponse transactionResponse;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(SpecialistService.class);
        response = Mockito.mock(SpecialistResponse.class);
        saveRequest = Mockito.mock(SpecialistSaveRequest.class);
        updateRequest = Mockito.mock(SpecialistUpdateInfo.class);
        loginRequest = Mockito.mock(SpecialistLoginRequest.class);
        homeServiceResponse = Mockito.mock(HomeServiceResponse.class);
        transactionResponse = Mockito.mock(TransactionResponse.class);
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
    void findAllHomeServicesBySpecialistId() {
        Mockito.when(service.findAllHomeServicesBySpecialistId(Mockito.anyLong()))
                .thenReturn(List.of(homeServiceResponse));
        assertEquals(List.of(homeServiceResponse),
                service.findAllHomeServicesBySpecialistId(Mockito.anyLong()));
    }




    @Test
    void findAllByScoreIsBetween() {
        Mockito.when(service.findAllByScoreIsBetween(Mockito.anyDouble(), Mockito.anyDouble()))
                .thenReturn(List.of(response));
        assertEquals(List.of(response),
                service.findAllByScoreIsBetween(Mockito.anyDouble(), Mockito.anyDouble()));
    }


    @Test
    void findScoreBySpecialistId() {
        Mockito.when(service.findScoreBySpecialistId(Mockito.anyLong()))
                .thenReturn(1.0);
        assertEquals(1.0, service.findScoreBySpecialistId(Mockito.anyLong()));
    }


    @Test
    void findAllTransactionsBySpecialistId() {
        Mockito.when(service.findAllTransactionsBySpecialistId(Mockito.anyLong()))
                .thenReturn(List.of(transactionResponse));
        assertEquals(List.of(transactionResponse),
                service.findAllTransactionsBySpecialistId(Mockito.anyLong()));
    }


    @Test
    void inActivateSpecialist() {
        Mockito.doNothing().when(service).inActivateSpecialist();
        Mockito.verify(service, Mockito.times(0))
                .inActivateSpecialist();
    }
}
*/
