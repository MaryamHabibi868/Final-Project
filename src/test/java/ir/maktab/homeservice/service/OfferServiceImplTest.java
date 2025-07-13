package ir.maktab.homeservice.service;

import ir.maktab.homeservice.dto.OfferResponse;
import ir.maktab.homeservice.dto.OfferSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class OfferServiceImplTest {

    private OfferService service;
    private OfferResponse response;
    private OfferSaveRequest request;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(OfferService.class);
        response = Mockito.mock(OfferResponse.class);
        request = Mockito.mock(OfferSaveRequest.class);
    }

    /*@AfterEach
    @BeforeAll (1 bar aval seda zade mishe)*/

    @Test
    void submitOfferToOrder() {
        Mockito.when(service.submitOfferToOrder(request)).thenReturn(response);
        assertEquals(response, service.submitOfferToOrder(request));
    }

    @Test
    void chooseOfferOfSpecialist() {
        Mockito.when(service.chooseOfferOfSpecialist(Mockito.anyLong())).thenReturn(response);
        assertEquals(response, service.chooseOfferOfSpecialist(Mockito.anyLong()));
    }

    @Test
    void startService() {
        Mockito.when(service.startService(Mockito.anyLong())).thenReturn(response);
        assertEquals(response, service.startService(Mockito.anyLong()));
    }

    @Test
    void endService() {
        Mockito.when(service.endService(Mockito.anyLong())).thenReturn(response);
        assertEquals(response, service.endService(Mockito.anyLong()));
    }

    @Test
    void findByOfferOfSpecialistId() {
        Mockito.when(service.findByOfferOfSpecialistId(Mockito.anyLong()))
                        .thenReturn(List.of(response));
        assertEquals(List.of(response), service.findByOfferOfSpecialistId(Mockito.anyLong()));
    }

    // void method
    @Test
    void paySpecialist() {
        Mockito.doNothing().when(service).paySpecialist(Mockito.anyLong());
        Mockito.verify(service , Mockito.times(0))
                .paySpecialist(Mockito.anyLong());
    }
}