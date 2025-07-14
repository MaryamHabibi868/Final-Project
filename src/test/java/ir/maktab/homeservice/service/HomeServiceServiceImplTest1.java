package ir.maktab.homeservice.service;

import ir.maktab.homeservice.dto.HomeServiceResponse;
import ir.maktab.homeservice.dto.HomeServiceSaveRequest;
import ir.maktab.homeservice.dto.HomeServiceUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class HomeServiceServiceImplTest1 {

    private HomeServiceService service;
    private HomeServiceResponse response;
    private HomeServiceSaveRequest saveRequest;
    private HomeServiceUpdateRequest updateRequest;


    @BeforeEach
    void setUp() {
        service = Mockito.mock(HomeServiceService.class);
        response = Mockito.mock(HomeServiceResponse.class);
        saveRequest = Mockito.mock(HomeServiceSaveRequest.class);
        updateRequest = Mockito.mock(HomeServiceUpdateRequest.class);
    }


    @Test
    void createHomeService() {
    Mockito.when(service.createHomeService(saveRequest)).thenReturn(response);
    assertEquals(response, service.createHomeService(saveRequest));
    }

    @Test
    void updateHomeService() {
        Mockito.when(service.updateHomeService(updateRequest)).thenReturn(response);
        assertEquals(response, service.updateHomeService(updateRequest));
    }

    @Test
    void deleteHomeService() {
        Mockito.doNothing().when(service).deleteHomeService(Mockito.anyLong());
        Mockito.verify(service, Mockito
                .times(0)).deleteHomeService(Mockito.anyLong());
    }

    @Test
    void findAllHomeServices() {
        Mockito.when(service.findAllHomeServices()).thenReturn(List.of(response));
        assertEquals(List.of(response), service.findAllHomeServices());
    }

    @Test
    void findHomeServiceById(){
        Mockito.when(service.findHomeServiceById(Mockito.anyLong())).thenReturn(response);
        assertEquals(response, service.findHomeServiceById(Mockito.anyLong()));
    }

    @Test
    void findAllHomeServiceByParentServiceId(){
        Mockito.when(service.findAllHomeServiceByParentServiceId(Mockito.anyLong()))
                .thenReturn(List.of(response));
        assertEquals(List.of(response), service.findAllHomeServiceByParentServiceId(Mockito.anyLong()));
    }
}