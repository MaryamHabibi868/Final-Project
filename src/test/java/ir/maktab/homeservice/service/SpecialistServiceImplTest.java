package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.domains.enumClasses.AccountStatus;
import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.*;
import ir.maktab.homeservice.mapper.*;
import ir.maktab.homeservice.repository.SpecialistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpecialistServiceImplTest {

    @Mock
    SpecialistRepository repository;

    @Mock
    SpecialistMapper specialistMapper;

    @Mock
    HomeServiceService homeServiceService;

    @Mock
    HomeServiceMapper homeServiceMapper;

    @Mock
    TransactionService transactionService;

    @Mock
    TransactionMapper transactionMapper;

    @InjectMocks
    SpecialistServiceImpl specialistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerSpecialist_success() {
        SpecialistSaveRequest req = new SpecialistSaveRequest();
        req.setEmail("test@example.com");
        req.setFirstName("Ali");
        req.setLastName("Ahmadi");
        req.setPassword("pass");
        req.setProfileImagePath(null);


        when(repository.existsByEmail(req.getEmail())).thenReturn(false);


        Specialist savedSpecialist = new Specialist();
        savedSpecialist.setId(1L);
        savedSpecialist.setEmail(req.getEmail());
        savedSpecialist.setStatus(AccountStatus.NEW);

        when(repository.save(any(Specialist.class))).thenReturn(savedSpecialist);


        SpecialistResponse specialistResponse = new SpecialistResponse();
        when(specialistMapper.entityMapToResponse(savedSpecialist)).thenReturn(specialistResponse);


        SpecialistResponse response = specialistService.registerSpecialist(req);


        assertNotNull(response);
        verify(repository).existsByEmail(req.getEmail());
        verify(repository).save(any(Specialist.class));
        verify(specialistMapper).entityMapToResponse(savedSpecialist);
    }

    @Test
    void registerSpecialist_throwsDuplicatedException() {
        SpecialistSaveRequest req = new SpecialistSaveRequest();
        req.setEmail("test@example.com");

        when(repository.existsByEmail(req.getEmail())).thenReturn(true);

        assertThrows(DuplicatedException.class, () -> specialistService.registerSpecialist(req));

        verify(repository).existsByEmail(req.getEmail());
        verify(repository, never()).save(any());
    }

    @Test
    void loginSpecialist_success() {
        SpecialistLoginRequest req = new SpecialistLoginRequest();
        req.setEmail("email");
        req.setPassword("pass");

        Specialist specialist = new Specialist();
        when(repository.findByEmailAndPassword(req.getEmail(), req.getPassword()))
                .thenReturn(Optional.of(specialist));
        when(specialistMapper.entityMapToResponse(specialist)).thenReturn(new SpecialistResponse());

        SpecialistResponse response = specialistService.loginSpecialist(req);

        assertNotNull(response);
        verify(repository).findByEmailAndPassword(req.getEmail(), req.getPassword());
        verify(specialistMapper).entityMapToResponse(specialist);
    }

    @Test
    void loginSpecialist_notFound() {
        SpecialistLoginRequest req = new SpecialistLoginRequest();
        req.setEmail("email");
        req.setPassword("pass");

        when(repository.findByEmailAndPassword(req.getEmail(), req.getPassword())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> specialistService.loginSpecialist(req));
    }

    @Test
    void updateSpecialistInfo_success() {
        SpecialistUpdateInfo req = new SpecialistUpdateInfo();
        req.setId(1L);
        req.setEmail("newemail");
        req.setPassword("newpass");
        req.setProfileImagePath("newpath");

        Specialist specialist = new Specialist();
        specialist.setId(1L);
        specialist.setStatus(AccountStatus.APPROVED);

        when(repository.findById(req.getId())).thenReturn(Optional.of(specialist));
        when(repository.existsByOffersStatusAndId(OfferStatus.ACCEPTED, req.getId())).thenReturn(false);
        when(repository.existsByOffersStatusAndId(OfferStatus.PENDING, req.getId())).thenReturn(false);
        when(repository.existsByEmail(req.getEmail())).thenReturn(false);
        when(repository.save(any())).thenReturn(specialist);
        when(specialistMapper.entityMapToResponse(specialist)).thenReturn(new SpecialistResponse());

        SpecialistResponse response = specialistService.updateSpecialistInfo(req);

        assertNotNull(response);
        assertEquals(AccountStatus.PENDING, specialist.getStatus());
        verify(repository).save(specialist);
        verify(specialistMapper).entityMapToResponse(specialist);
    }

    @Test
    void updateSpecialistInfo_throwsNotFound() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> specialistService.updateSpecialistInfo(new SpecialistUpdateInfo()));
    }

    @Test
    void updateSpecialistInfo_throwsNotApproved_activeOffers() {
        SpecialistUpdateInfo req = new SpecialistUpdateInfo();
        req.setId(1L);
        Specialist specialist = new Specialist();
        specialist.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(specialist));
        when(repository.existsByOffersStatusAndId(OfferStatus.ACCEPTED, 1L)).thenReturn(true);

        assertThrows(NotApprovedException.class, () -> specialistService.updateSpecialistInfo(req));
    }

    @Test
    void updateSpecialistInfo_throwsNotApproved_pendingOffers() {
        SpecialistUpdateInfo req = new SpecialistUpdateInfo();
        req.setId(1L);
        Specialist specialist = new Specialist();
        specialist.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(specialist));
        when(repository.existsByOffersStatusAndId(OfferStatus.ACCEPTED, 1L)).thenReturn(false);
        when(repository.existsByOffersStatusAndId(OfferStatus.PENDING, 1L)).thenReturn(true);

        assertThrows(NotApprovedException.class, () -> specialistService.updateSpecialistInfo(req));
    }

    @Test
    void updateSpecialistInfo_throwsDuplicatedEmail() {
        SpecialistUpdateInfo req = new SpecialistUpdateInfo();
        req.setId(1L);
        req.setEmail("email");

        Specialist specialist = new Specialist();
        specialist.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(specialist));
        when(repository.existsByOffersStatusAndId(OfferStatus.ACCEPTED, 1L)).thenReturn(false);
        when(repository.existsByOffersStatusAndId(OfferStatus.PENDING, 1L)).thenReturn(false);
        when(repository.existsByEmail(req.getEmail())).thenReturn(true);

        assertThrows(DuplicatedException.class, () -> specialistService.updateSpecialistInfo(req));
    }

    @Test
    void approveSpecialistRegistration_success() {
        Specialist specialist = new Specialist();
        specialist.setId(1L);
        specialist.setStatus(AccountStatus.NEW);

        when(repository.findById(1L)).thenReturn(Optional.of(specialist));
        when(repository.save(specialist)).thenReturn(specialist);
        when(specialistMapper.entityMapToResponse(specialist)).thenReturn(new SpecialistResponse());

        SpecialistResponse response = specialistService.approveSpecialistRegistration(1L);

        assertNotNull(response);
        assertEquals(AccountStatus.APPROVED, specialist.getStatus());
        verify(repository).save(specialist);
        verify(specialistMapper).entityMapToResponse(specialist);
    }

    @Test
    void approveSpecialistRegistration_alreadyApproved() {
        Specialist specialist = new Specialist();
        specialist.setStatus(AccountStatus.APPROVED);

        when(repository.findById(1L)).thenReturn(Optional.of(specialist));
        when(specialistMapper.entityMapToResponse(specialist)).thenReturn(new SpecialistResponse());

        SpecialistResponse response = specialistService.approveSpecialistRegistration(1L);

        assertNotNull(response);
        verify(repository, never()).save(any());
    }

    @Test
    void approveSpecialistRegistration_notFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> specialistService.approveSpecialistRegistration(1L));
    }

    @Test
    void addSpecialistToHomeService_success() {
        Specialist specialist = new Specialist();
        specialist.setId(1L);
        specialist.setStatus(AccountStatus.APPROVED);
        specialist.setHomeServices(new HashSet<>());

        HomeService homeService = new HomeService();
        homeService.setId(2L);

        when(repository.findById(1L)).thenReturn(Optional.of(specialist));
        when(homeServiceService.findById(2L)).thenReturn(homeService);
        when(repository.save(specialist)).thenReturn(specialist);
        when(homeServiceService.save(homeService)).thenReturn(homeService);

        specialistService.addSpecialistToHomeService(1L, 2L);

        assertTrue(specialist.getHomeServices().contains(homeService));
        verify(repository).save(specialist);
        verify(homeServiceService).save(homeService);
    }

    @Test
    void addSpecialistToHomeService_notApproved() {
        Specialist specialist = new Specialist();
        specialist.setStatus(AccountStatus.PENDING);

        when(repository.findById(1L)).thenReturn(Optional.of(specialist));

        assertThrows(NotApprovedException.class, () -> specialistService.addSpecialistToHomeService(1L, 2L));
    }

    @Test
    void addSpecialistToHomeService_specialistNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> specialistService.addSpecialistToHomeService(1L, 2L));
    }

    @Test
    void removeSpecialistFromHomeService_success() {
        Specialist specialist = new Specialist();
        specialist.setId(1L);
        specialist.setStatus(AccountStatus.APPROVED);
        HomeService homeService = new HomeService();
        homeService.setId(2L);
        specialist.setHomeServices(new HashSet<>(Set.of(homeService)));

        when(repository.findById(1L)).thenReturn(Optional.of(specialist));
        when(homeServiceService.findById(2L)).thenReturn(homeService);
        when(repository.save(specialist)).thenReturn(specialist);
        when(homeServiceService.save(homeService)).thenReturn(homeService);

        specialistService.removeSpecialistFromHomeService(1L, 2L);

        assertFalse(specialist.getHomeServices().contains(homeService));
        verify(repository).save(specialist);
        verify(homeServiceService).save(homeService);
    }

    @Test
    void removeSpecialistFromHomeService_notApproved() {
        Specialist specialist = new Specialist();
        specialist.setStatus(AccountStatus.PENDING);

        when(repository.findById(1L)).thenReturn(Optional.of(specialist));

        assertThrows(NotApprovedException.class, () -> specialistService.removeSpecialistFromHomeService(1L, 2L));
    }

    @Test
    void removeSpecialistFromHomeService_specialistNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> specialistService.removeSpecialistFromHomeService(1L, 2L));
    }

    @Test
    void findAllHomeServicesBySpecialistId_success() {
        Pageable pageable = PageRequest.of(0, 10);
        HomeService homeService = new HomeService();
        Page<HomeService> page = new PageImpl<>(List.of(homeService));

        when(repository.findHomeServicesBySpecialistId(1L, pageable)).thenReturn(page);
        when(homeServiceMapper.entityMapToResponse(homeService)).thenReturn(new HomeServiceResponse());

        Page<HomeServiceResponse> responses = specialistService.findAllHomeServicesBySpecialistId(1L, pageable);

        assertNotNull(responses);
        assertEquals(1, responses.getContent().size());
    }

    @Test
    void findAllByHomeServiceId_success() {
        Pageable pageable = PageRequest.of(0, 10);
        Specialist specialist = new Specialist();
        Page<Specialist> page = new PageImpl<>(List.of(specialist));

        when(repository.findAllByHomeServices_id(2L, pageable)).thenReturn(page);
        when(specialistMapper.entityMapToResponse(specialist)).thenReturn(new SpecialistResponse());

        Page<SpecialistResponse> responses = specialistService.findAllByHomeServiceId(2L, pageable);

        assertNotNull(responses);
        assertEquals(1, responses.getContent().size());
    }

    @Test
    void findAllByScoreIsBetween_success() {
        Pageable pageable = PageRequest.of(0, 10);
        Specialist specialist = new Specialist();
        Page<Specialist> page = new PageImpl<>(List.of(specialist));

        when(repository.findAllByScoreIsBetween(0.0, 10.0, pageable)).thenReturn(page);
        when(specialistMapper.entityMapToResponse(specialist)).thenReturn(new SpecialistResponse());

        Page<SpecialistResponse> responses = specialistService.findAllByScoreIsBetween(0.0, 10.0, pageable);

        assertNotNull(responses);
        assertEquals(1, responses.getContent().size());
    }

    @Test
    void findScoreBySpecialistId_success() {
        Specialist specialist = new Specialist();
        specialist.setScore(4.5);

        when(repository.findById(1L)).thenReturn(Optional.of(specialist));

        Double score = specialistService.findScoreBySpecialistId(1L);

        assertEquals(4.5, score);
    }

    @Test
    void findScoreBySpecialistId_notFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> specialistService.findScoreBySpecialistId(1L));
    }

    @Test
    void findAllTransactionsBySpecialistId_success() {
        Pageable pageable = PageRequest.of(0, 10);

        Wallet wallet = new Wallet();
        wallet.setId(5L);

        Specialist specialist = new Specialist();
        specialist.setId(1L);
        specialist.setWallet(wallet);


        Transaction transaction = new Transaction();
        List<Transaction> transactionList = List.of(transaction);
        Page<Transaction> transactionPage = new PageImpl<>(transactionList);


        TransactionResponse transactionResponse = new TransactionResponse();


        when(repository.findById(1L)).thenReturn(Optional.of(specialist));
        when(transactionService.findAllByWalletId(5L, pageable)).thenReturn(transactionPage);
        when(transactionMapper.entityMapToResponse(transaction)).thenReturn(transactionResponse);


        Page<TransactionResponse> result = specialistService.findAllTransactionsBySpecialistId(1L, pageable);


        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertSame(transactionResponse, result.getContent().get(0));
    }

    @Test
    void findAllTransactionsBySpecialistId_notFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> specialistService.findAllTransactionsBySpecialistId(1L, PageRequest.of(0, 10)));
    }

    @Test
    void inActivateSpecialist_success() {
        Specialist specialist1 = new Specialist();
        specialist1.setStatus(AccountStatus.APPROVED);
        Specialist specialist2 = new Specialist();
        specialist2.setStatus(AccountStatus.APPROVED);

        when(repository.findAllByScoreIsLessThan(0.0)).thenReturn(List.of(specialist1, specialist2));

        specialistService.inActivateSpecialist();

        assertEquals(AccountStatus.INACTIVE, specialist1.getStatus());
        assertEquals(AccountStatus.INACTIVE, specialist2.getStatus());
    }
}
