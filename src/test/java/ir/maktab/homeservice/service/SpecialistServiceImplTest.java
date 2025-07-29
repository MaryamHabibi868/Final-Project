package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.domains.enumClasses.*;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.mapper.*;
import ir.maktab.homeservice.repository.SpecialistRepository;
import ir.maktab.homeservice.security.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.*;
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
    private HomeServiceMapper homeServiceMapper;
    @Mock
    private TransactionService transactionService;
    @Mock
    private TransactionMapper transactionMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private SecurityUtil securityUtil;
    @Mock
    private VerificationTokenService verificationTokenService;
    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerSpecialist_shouldRegisterAndSendEmail() {
        SpecialistSaveRequest request = new SpecialistSaveRequest();
        request.setFirstName("Ali");
        request.setLastName("Test");
        request.setEmail("test@example.com");
        request.setPassword("pass");
        request.setProfileImagePath("image.png");

        when(repository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPass");

        Specialist specialist = new Specialist();
        specialist.setId(1L);
        specialist.setEmail(request.getEmail());
        Wallet wallet = new Wallet();
        specialist.setWallet(wallet);

        when(repository.save(any())).thenReturn(specialist);
        when(specialistMapper.entityMapToResponse(any()))
                .thenReturn(new SpecialistResponse());

        SpecialistResponse response = specialistService
                .registerSpecialist(request);

        assertNotNull(response);
        verify(emailService).sendVerificationEmail(
                eq("test@example.com"), anyString());
    }


    @Test
    void sendVerificationEmail_shouldSaveTokenAndSendEmail() {
        Specialist specialist = new Specialist();
        specialist.setEmail("test@example.com");

        doNothing().when(emailService).sendVerificationEmail(
                anyString(), anyString());
        when(verificationTokenService.save(any()))
                .thenReturn(new VerificationToken());

        specialistService.sendVerificationEmail(specialist);

        verify(emailService).sendVerificationEmail(
                eq("test@example.com"), anyString());
        verify(verificationTokenService).save(any());
    }

    @Test
    void verifySpecialistEmail_shouldVerifyAndActivate() {
        VerificationToken token = new VerificationToken();
        token.setToken("abc");
        token.setUsed(false);
        token.setExpiryDate(LocalDateTime.now().plusHours(1));
        Specialist specialist = new Specialist();
        specialist.setStatus(AccountStatus.NEW);
        token.setUser(specialist);

        when(verificationTokenService.findByToken("abc"))
                .thenReturn(Optional.of(token));
        when(repository.save(any())).thenReturn(specialist);
        when(verificationTokenService.save(any())).thenReturn(token);
        when(specialistMapper.entityMapToVerifiedUserResponse(any()))
                .thenReturn(new VerifiedUserResponse());

        VerifiedUserResponse response = specialistService
                .verifySpecialistEmail("abc");

        assertNotNull(response);
        assertTrue(specialist.getIsEmailVerify());
    }

    @Test
    void loginSpecialist_shouldReturnResponse() {
        Specialist specialist = new Specialist();
        when(repository.findByEmailAndPassword(
                "test@example.com", "pass"))
                .thenReturn(Optional.of(specialist));
        when(specialistMapper.entityMapToResponse(specialist))
                .thenReturn(new SpecialistResponse());

        SpecialistResponse response = specialistService
                .loginSpecialist(new SpecialistLoginRequest(
                        "test@example.com", "pass"));

        assertNotNull(response);
    }

    @Test
    void updateSpecialistInfo_shouldUpdateAndReturnResponse() {
        SpecialistUpdateInfo request = new SpecialistUpdateInfo();
        request.setEmail("new@example.com");
        request.setPassword("newpass");

        Specialist specialist = new Specialist();
        specialist.setId(1L);
        when(securityUtil.getCurrentUsername())
                .thenReturn("old@example.com");
        when(repository.findByEmail("old@example.com"))
                .thenReturn(Optional.of(specialist));
        when(repository.existsByOffersStatusAndId(any(), anyLong()))
                .thenReturn(false);
        when(repository.existsByEmail("new@example.com")).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(repository.save(any())).thenReturn(specialist);
        when(specialistMapper.entityMapToResponse(specialist))
                .thenReturn(new SpecialistResponse());

        SpecialistResponse response = specialistService
                .updateSpecialistInfo(request);

        assertNotNull(response);
    }

    @Test
    void approveSpecialistRegistration_shouldApproveAndReturnResponse() {
        Specialist specialist = new Specialist();
        specialist.setId(1L);
        specialist.setStatus(AccountStatus.PENDING);
        specialist.setIsEmailVerify(true);

        when(repository.findById(1L)).thenReturn(Optional.of(specialist));
        when(repository.save(specialist)).thenReturn(specialist);
        when(specialistMapper.entityMapToResponse(specialist))
                .thenReturn(new SpecialistResponse());

        SpecialistResponse response = specialistService
                .approveSpecialistRegistration(1L);

        assertNotNull(response);
    }

    @Test
    void addSpecialistToHomeService_shouldAddAndReturnResponse() {
        Specialist specialist = new Specialist();
        specialist.setId(1L);
        specialist.setStatus(AccountStatus.APPROVED);
        specialist.setHomeServices(new HashSet<>());

        HomeService homeService = new HomeService();

        when(repository.findById(1L)).thenReturn(Optional.of(specialist));
        when(homeServiceService.findById(2L)).thenReturn(homeService);
        when(repository.save(any())).thenReturn(specialist);
        when(specialistMapper.entityMapToAddRemoveSToHResponse(specialist))
                .thenReturn(new AddRemoveSToHResponse());

        AddRemoveSToHResponse response = specialistService
                .addSpecialistToHomeService(1L, 2L);

        assertNotNull(response);
    }

    @Test
    void removeSpecialistFromHomeService_shouldRemoveAndReturnResponse() {
        Specialist specialist = new Specialist();
        specialist.setStatus(AccountStatus.APPROVED);
        specialist.setHomeServices(new HashSet<>());

        HomeService homeService = new HomeService();
        specialist.getHomeServices().add(homeService);

        when(repository.findById(1L)).thenReturn(Optional.of(specialist));
        when(homeServiceService.findById(2L)).thenReturn(homeService);
        when(repository.save(any())).thenReturn(specialist);
        when(specialistMapper.entityMapToAddRemoveSToHResponse(specialist))
                .thenReturn(new AddRemoveSToHResponse());

        AddRemoveSToHResponse response = specialistService
                .removeSpecialistFromHomeService(1L, 2L);

        assertNotNull(response);
    }

    @Test
    void findAllHomeServicesBySpecialistId_shouldReturnPage() {
        Page<HomeService> page = new PageImpl<>(List.of(new HomeService()));
        when(repository.findHomeServicesBySpecialistId(eq(1L), any()))
                .thenReturn(page);
        when(homeServiceMapper.entityMapToResponse(any()))
                .thenReturn(new HomeServiceResponse());

        Page<HomeServiceResponse> result = specialistService
                .findAllHomeServicesBySpecialistId(1L, Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void findAllByHomeServiceId_shouldReturnPage() {
        Page<Specialist> page = new PageImpl<>(List.of(new Specialist()));
        when(repository.findAllByHomeServices_id(eq(1L), any()))
                .thenReturn(page);
        when(specialistMapper.entityMapToResponse(any()))
                .thenReturn(new SpecialistResponse());

        Page<SpecialistResponse> result = specialistService
                .findAllByHomeServiceId(1L, Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void findAllByScoreIsBetween_shouldReturnPage() {
        Page<Specialist> page = new PageImpl<>(List.of(new Specialist()));
        when(repository.findAllByScoreIsBetween(eq(0.0),
                eq(5.0), any())).thenReturn(page);
        when(specialistMapper.entityMapToResponse(any()))
                .thenReturn(new SpecialistResponse());

        Page<SpecialistResponse> result = specialistService
                .findAllByScoreIsBetween(0.0, 5.0,
                        Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void findScoreBySpecialistId_shouldReturnScoreResponse() {
        Specialist specialist = new Specialist();
        when(securityUtil.getCurrentUsername())
                .thenReturn("test@example.com");
        when(repository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(specialist));
        when(specialistMapper.entityMapToScoreResponse(any()))
                .thenReturn(new ScoreResponse());

        ScoreResponse result = specialistService.findScoreBySpecialistId();

        assertNotNull(result);
    }

    @Test
    void findAllTransactionsBySpecialistId_shouldReturnTransactions() {
        Specialist specialist = new Specialist();
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        specialist.setWallet(wallet);

        when(securityUtil.getCurrentUsername())
                .thenReturn("test@example.com");
        when(repository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(specialist));
        Page<Transaction> page = new PageImpl<>(List.of(new Transaction()));
        when(transactionService.findAllByWalletId(1L, Pageable.unpaged()))
                .thenReturn(page);
        when(transactionMapper.entityMapToResponse(any()))
                .thenReturn(new TransactionResponse());

        Page<TransactionResponse> result = specialistService
                .findAllTransactionsBySpecialistId(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void inActivateSpecialist_shouldSetInactive() {
        Specialist s1 = new Specialist();
        s1.setScore(-1.0);
        List<Specialist> list = new ArrayList<>();
        list.add(s1);

        when(repository.findAllByScoreIsLessThan(0.0)).thenReturn(list);

        specialistService.inActivateSpecialist();

        assertEquals(AccountStatus.INACTIVE, s1.getStatus());
    }

    @Test
    void findByEmail_shouldReturnSpecialist() {
        Specialist specialist = new Specialist();
        when(repository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(specialist));

        Specialist result = specialistService.findByEmail("test@example.com");

        assertEquals(specialist, result);
    }
}
