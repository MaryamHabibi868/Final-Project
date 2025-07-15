/*
package ir.maktab.homeservice.service;

import ir.maktab.homeservice.HomeServiceApplication;
import ir.maktab.homeservice.dto.HomeServiceResponse;
import ir.maktab.homeservice.dto.HomeServiceSaveRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

//                          esme class
@SpringBootTest(classes = {HomeServiceApplication.class})

// @order:  method ha be tartibe khasi ejra beshe . az 1 mitounim olyaviyat midim
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

//@BeforeAll: method static bayad beshavad. ba in @ dg method static nemishe
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HomeServiceServiceImplTest {

    // filed injection
    @Autowired
    private HomeServiceService service;
    private HomeServiceSaveRequest request;
    private HomeServiceResponse response;

    @BeforeAll
    void setUp() {
        request = new HomeServiceSaveRequest();
        request.setTitle("test");
        request.setDescription("test");
        request.setBasePrice(BigDecimal.valueOf(10.00));
    }



    // return type
    @Order(1)
    @Test
    void createHomeService() {
        response = service.createHomeService(request);
    }

    @Test
    void updateHomeService() {
    }


    // void method
    @Order(4)
    @Test
    void deleteHomeService() {
        service.deleteHomeService(response.getId());
    }

    @Order(3)
    @Test
    void findAllHomeServices() {
        System.out.println(service.findAllHomeServices());
    }
}
*/
