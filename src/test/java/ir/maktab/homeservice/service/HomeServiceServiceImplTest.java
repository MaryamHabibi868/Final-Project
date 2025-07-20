package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.dto.HomeServiceResponse;
import ir.maktab.homeservice.dto.HomeServiceSaveRequest;
import ir.maktab.homeservice.dto.HomeServiceUpdateRequest;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.HomeServiceMapper;
import ir.maktab.homeservice.repository.HomeServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import java.math.BigDecimal;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HomeServiceServiceImplTest {

    @Mock
    private HomeServiceRepository repository;

    @Mock
    private HomeServiceMapper mapper;

    @InjectMocks
    private HomeServiceServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createHomeService_success_shouldReturnResponse() {
        HomeServiceSaveRequest request = new HomeServiceSaveRequest();
        request.setTitle("Cleaning");
        request.setBasePrice(BigDecimal.valueOf(100));
        request.setDescription("Home cleaning service");

        when(repository.findAllByTitleIgnoreCase("Cleaning"))
                .thenReturn(Optional.empty());

        HomeService saved = new HomeService();
        saved.setId(1L);
        saved.setTitle("Cleaning");
        saved.setBasePrice(BigDecimal.valueOf(100));
        saved.setDescription("Home cleaning service");

        when(repository.save(any(HomeService.class))).thenReturn(saved);

        HomeServiceResponse response = new HomeServiceResponse();
        response.setId(1L);
        response.setTitle("Cleaning");
        response.setBasePrice(BigDecimal.valueOf(100));
        response.setDescription("Home cleaning service");

        when(mapper.entityMapToResponse(saved)).thenReturn(response);

        HomeServiceResponse result = service.createHomeService(request);

        assertNotNull(result);
        assertEquals("Cleaning", result.getTitle());
        verify(repository).findAllByTitleIgnoreCase("Cleaning");
        verify(repository).save(any(HomeService.class));
        verify(mapper).entityMapToResponse(saved);
    }

    @Test
    void createHomeService_shouldThrowDuplicatedException_whenTitleExists() {
        HomeServiceSaveRequest request = new HomeServiceSaveRequest();
        request.setTitle("Cleaning");

        HomeService existing = new HomeService();
        existing.setId(1L);
        existing.setTitle("Cleaning");

        when(repository.findAllByTitleIgnoreCase("Cleaning"))
                .thenReturn(Optional.of(existing));

        DuplicatedException exception = assertThrows(DuplicatedException.class,
                () -> service.createHomeService(request));

        assertEquals("Home Service Title Already Exists", exception.getMessage());
    }

    @Test
    void createHomeService_shouldThrowNotFoundException_whenParentNotFound() {
        HomeServiceSaveRequest request = new HomeServiceSaveRequest();
        request.setTitle("Cleaning");
        request.setParentServiceId(10L);

        when(repository.findAllByTitleIgnoreCase("Cleaning"))
                .thenReturn(Optional.empty());

        when(repository.findById(10L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.createHomeService(request));

        assertEquals("Parent Service Not Found", exception.getMessage());
    }

    @Test
    void updateHomeService_success_shouldUpdateAndReturnResponse() {
        HomeServiceUpdateRequest request = new HomeServiceUpdateRequest();
        request.setId(1L);
        request.setTitle("New Title");
        request.setBasePrice(BigDecimal.valueOf(200));
        request.setDescription("Updated description");
        request.setParentServiceId(2L);

        HomeService existing = new HomeService();
        existing.setId(1L);
        existing.setTitle("Old Title");
        existing.setBasePrice(BigDecimal.valueOf(100));
        existing.setDescription("Old description");

        HomeService parentService = new HomeService();
        parentService.setId(2L);
        parentService.setTitle("Parent Service");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.findById(2L)).thenReturn(Optional.of(parentService));
        when(repository.save(any(HomeService.class))).thenAnswer(invocation -> invocation.getArgument(0));

        HomeServiceResponse response = new HomeServiceResponse();
        response.setId(1L);
        response.setTitle("New Title");
        response.setBasePrice(BigDecimal.valueOf(200));
        response.setDescription("Updated description");

        when(mapper.entityMapToResponse(any(HomeService.class))).thenReturn(response);

        HomeServiceResponse result = service.updateHomeService(request);

        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
        assertEquals(BigDecimal.valueOf(200), result.getBasePrice());
        assertEquals("Updated description", result.getDescription());
        verify(repository).findById(1L);
        verify(repository).findById(2L);
        verify(repository).save(any(HomeService.class));
        verify(mapper).entityMapToResponse(any(HomeService.class));
    }

    @Test
    void updateHomeService_shouldThrowNotFoundException_whenHomeServiceNotFound() {
        HomeServiceUpdateRequest request = new HomeServiceUpdateRequest();
        request.setId(99L);

        when(repository.findById(99L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.updateHomeService(request));

        assertEquals("Home Service Not Found", exception.getMessage());
    }

    @Test
    void updateHomeService_shouldThrowNotFoundException_whenParentServiceNotFound() {
        HomeServiceUpdateRequest request = new HomeServiceUpdateRequest();
        request.setId(1L);
        request.setParentServiceId(999L);

        HomeService existing = new HomeService();
        existing.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.findById(999L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.updateHomeService(request));

        assertEquals("Parent Home Service Not Found", exception.getMessage());
    }

    @Test
    void deleteHomeService_shouldCallDeleteById() {
        Long id = 10L;
        HomeService existing = new HomeService();
        existing.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        doNothing().when(repository).deleteById(id);

        service.deleteHomeService(id);

        verify(repository).findById(id);
        verify(repository).deleteById(id);
    }

    @Test
    void findAllHomeServices_shouldReturnPagedResponse() {
        Pageable pageable = PageRequest.of(0, 10);
        List<HomeService> list = new ArrayList<>();

        HomeService hs1 = new HomeService();
        hs1.setId(1L);
        hs1.setTitle("Service 1");

        HomeService hs2 = new HomeService();
        hs2.setId(2L);
        hs2.setTitle("Service 2");

        list.add(hs1);
        list.add(hs2);

        Page<HomeService> page = new PageImpl<>(list, pageable, list.size());

        when(repository.findAll(pageable)).thenReturn(page);

        when(mapper.entityMapToResponse(hs1)).thenReturn(new HomeServiceResponse() {{
            setId(1L);
            setTitle("Service 1");
        }});
        when(mapper.entityMapToResponse(hs2)).thenReturn(new HomeServiceResponse() {{
            setId(2L);
            setTitle("Service 2");
        }});

        Page<HomeServiceResponse> result = service.findAllHomeServices(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(repository).findAll(pageable);
        verify(mapper, times(2)).entityMapToResponse(any(HomeService.class));
    }

    @Test
    void findHomeServiceById_shouldReturnResponse() {
        Long id = 1L;
        HomeService homeService = new HomeService();
        homeService.setId(id);
        homeService.setTitle("Service 1");

        when(repository.findById(id)).thenReturn(Optional.of(homeService));

        HomeServiceResponse response = new HomeServiceResponse();
        response.setId(id);
        response.setTitle("Service 1");

        when(mapper.entityMapToResponse(homeService)).thenReturn(response);

        HomeServiceResponse result = service.findHomeServiceById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Service 1", result.getTitle());
        verify(repository).findById(id);
        verify(mapper).entityMapToResponse(homeService);
    }

    @Test
    void findHomeServiceById_shouldThrowNotFoundException_whenNotFound() {
        Long id = 99L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.findHomeServiceById(id));

        assertEquals("Home service Not found", exception.getMessage());
    }

    @Test
    void findAllHomeServiceByParentServiceId_shouldReturnPagedResponse() {
        Long parentId = 5L;
        Pageable pageable = PageRequest.of(0, 10);

        List<HomeService> list = new ArrayList<>();

        HomeService child1 = new HomeService();
        child1.setId(11L);
        child1.setTitle("Child 1");

        HomeService child2 = new HomeService();
        child2.setId(12L);
        child2.setTitle("Child 2");

        list.add(child1);
        list.add(child2);

        Page<HomeService> page = new PageImpl<>(list, pageable, list.size());

        when(repository.findAllByParentService_Id(parentId, pageable)).thenReturn(page);

        when(mapper.entityMapToResponse(child1)).thenReturn(new HomeServiceResponse() {{
            setId(11L);
            setTitle("Child 1");
        }});
        when(mapper.entityMapToResponse(child2)).thenReturn(new HomeServiceResponse() {{
            setId(12L);
            setTitle("Child 2");
        }});

        Page<HomeServiceResponse> result = service.findAllHomeServiceByParentServiceId(parentId, pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(repository).findAllByParentService_Id(parentId, pageable);
        verify(mapper, times(2)).entityMapToResponse(any(HomeService.class));
    }
}
