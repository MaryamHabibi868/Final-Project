package ir.maktab.homeservice;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.NotActiveException;
import ir.maktab.homeservice.exception.NotApprovedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.SpecialistMapper;
import ir.maktab.homeservice.repository.SpecialistRepository;
import ir.maktab.homeservice.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialistServiceImplTest {

    @Mock private SpecialistRepository specialistRepository;
    @Mock private SpecialistMapper specialistMapper;
    @Mock private HomeServiceService homeServiceService;
    @Mock private OrderOfCustomerService orderOfCustomerService;
    @Mock private OfferOfSpecialistService offerOfSpecialistService;

    @InjectMocks
    private SpecialistServiceImpl specialistService;

    private Specialist specialist;

    @BeforeEach
    void setup() {
        specialist = new Specialist();
        specialist.setId(1L);
        specialist.setEmail("maryam@example.com");
        specialist.setPassword("pass123");
        specialist.setIsActive(true);
        specialist.setAccountStatus(AccountStatus.APPROVED);
        specialist.setHomeServices(Set.of());
    }

    @Test
    void customDeleteSpecialistById_shouldDeactivateSpecialistIfFound() {
        when(specialistRepository.findById(1L)).thenReturn(Optional.of(specialist));

        specialistService.customDeleteSpecialistById(1L);

        verify(specialistRepository).save(any(Specialist.class));
    }

    @Test
    void registerSpecialist_shouldSaveAndReturnDto() {
        SpecialistSaveUpdateRequest request = new SpecialistSaveUpdateRequest();
        request.setEmail("maryam@example.com");

        when(specialistRepository.save(any())).thenReturn(specialist);
        when(specialistMapper.specialistMapToDTO(specialist)).thenReturn(request);

        SpecialistSaveUpdateRequest result = specialistService.registerSpecialist(request);

        assertEquals("maryam@example.com", result.getEmail());
    }

    @Test
    void loginSpecialist_shouldReturnDtoIfExists() {
        SpecialistSaveUpdateRequest expected = new SpecialistSaveUpdateRequest();
        expected.setEmail("maryam@example.com");

        when(specialistRepository.findByEmailAndPassword(any(), any())).thenReturn(Optional.of(specialist));
        when(specialistMapper.specialistMapToDTO(specialist)).thenReturn(expected);

        SpecialistSaveUpdateRequest result = specialistService.loginSpecialist(expected);

        assertEquals(expected.getEmail(), result.getEmail());
    }

    @Test
    void updateSpecialistInfo_shouldUpdateAndReturnDto() {
        SpecialistUpdateInfo request = new SpecialistUpdateInfo();
        request.setId(1L);
        request.setEmail("new@example.com");
        request.setPassword("newpass");

        when(specialistRepository.findById(1L)).thenReturn(Optional.of(specialist));
        when(specialistMapper.updateInfoMapToEntity(request)).thenReturn(specialist);
        when(specialistMapper.specialistMapToDTO(any())).thenReturn(new SpecialistSaveUpdateRequest());

        SpecialistSaveUpdateRequest result = specialistService.updateSpecialistInfo(request);

        assertNotNull(result);
        verify(specialistRepository).save(any());
    }

    @Test
    void approveSpecialistRegistration_shouldApproveIfNotAlreadyApproved() {
        SpecialistFound request = new SpecialistFound();
        request.setId(1L);

        when(specialistRepository.findById(1L)).thenReturn(Optional.of(specialist));
        when(specialistMapper.foundSpecialistToEntity(request)).thenReturn(specialist);
        when(specialistMapper.specialistMapToDTO(any())).thenReturn(new SpecialistSaveUpdateRequest());

        SpecialistSaveUpdateRequest result = specialistService.approveSpecialistRegistration(request);

        assertNotNull(result);
        verify(specialistRepository).save(any());
    }

    @Test
    void addSpecialistToHomeService_shouldAddWhenValid() {
        HomeService homeService = new HomeService();
        homeService.setId(2L);
        homeService.setIsActive(true);

        SpecialistFound specialistFound = new SpecialistFound();
        specialistFound.setId(1L);
        HomeServiceFound homeServiceFound = new HomeServiceFound();
        homeServiceFound.setId(2L);

        when(specialistRepository.findById(1L)).thenReturn(Optional.of(specialist));
        when(homeServiceService.findById(2L)).thenReturn(Optional.of(homeService));

        specialistService.addSpecialistToHomeService(specialistFound, homeServiceFound);

        verify(specialistRepository).save(any());
    }

    @Test
    void removeSpecialistFromHomeService_shouldRemoveWhenValid() {
        HomeService homeService = new HomeService();
        homeService.setId(2L);
        homeService.setIsActive(true);

        SpecialistFound specialistFound = new SpecialistFound();
        specialistFound.setId(1L);
        HomeServiceFound homeServiceFound = new HomeServiceFound();
        homeServiceFound.setId(2L);

        when(specialistRepository.findById(1L)).thenReturn(Optional.of(specialist));
        when(homeServiceService.findById(2L)).thenReturn(Optional.of(homeService));

        specialistService.removeSpecialistFromHomeService(specialistFound, homeServiceFound);

        verify(specialistRepository).save(any());
    }

    @Test
    void submitOfferBySpecialist_shouldSubmitWhenOrderStatusIsWaiting() {
        OrderOfCustomer order = new OrderOfCustomer();
        order.setId(1L);
        order.setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_OFFER);

        OfferOfSpecialistRequest request = new OfferOfSpecialistRequest();

        when(orderOfCustomerService.findById(1L)).thenReturn(Optional.of(order));
        when(offerOfSpecialistService.submitOffer(request)).thenReturn(request);

        OfferOfSpecialistRequest result = specialistService.submitOfferBySpecialist(request, order);

        assertNotNull(result);
        assertEquals(OrderStatus.WAITING_FOR_CHOOSING_SPECIALIST, order.getOrderStatus());
        verify(orderOfCustomerService).save(order);
    }
}
