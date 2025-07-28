package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.domains.enumClasses.AccountStatus;
import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotApprovedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.HomeServiceMapper;
import ir.maktab.homeservice.mapper.SpecialistMapper;
import ir.maktab.homeservice.mapper.TransactionMapper;
import ir.maktab.homeservice.repository.SpecialistRepository;
import ir.maktab.homeservice.security.SecurityUtil;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpecialistServiceImplTest {

    @InjectMocks
    private SpecialistServiceImpl specialistService;

    @Mock
    private SpecialistRepository repository;

    @Mock
    private SpecialistMapper specialistMapper;

    @Mock
    private HomeServiceService homeServiceService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private TransactionMapper transactionMapper;


    @Mock
    private HomeServiceMapper homeServiceMapper;

    @Mock
    private VerificationTokenService verificationTokenService;

    @Mock
    private EmailService emailService;

    private Pageable pageable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        specialistService = new SpecialistServiceImpl(
                repository, specialistMapper, homeServiceService,
                null, transactionService, null, passwordEncoder,
                securityUtil, verificationTokenService , emailService
        );

        MockitoAnnotations.openMocks(this);
        pageable = PageRequest.of(0, 10, Sort.by("id"));
    }

    @Test
    void testVerifySpecialistEmail_TokenNotFound_ThrowsException() {
        when(verificationTokenService.findByToken("token")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> specialistService.verifySpecialistEmail("token"));
    }



    @Test
    void testFindByEmail_NotFound_ThrowsException() {
        when(repository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> specialistService.findByEmail("notfound@example.com"));
    }







    @Test
    void approveSpecialistRegistration_notFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> specialistService.approveSpecialistRegistration(1L));
    }



    @Test
    void testAddSpecialistToHomeService_notFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> specialistService.addSpecialistToHomeService(1L, 2L));
    }




    @Test
    void testRemoveSpecialistFromHomeService_notFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> specialistService.removeSpecialistFromHomeService(1L, 2L));
    }



    @Test
    void testFindByEmail_shouldThrowNotFoundException_whenEmailNotFound() {
        // Arrange
        String email = "notfound@example.com";
        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> specialistService.findByEmail(email))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Specialist Not Found");
    }
}
