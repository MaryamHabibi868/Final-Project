package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.VerificationToken;
import ir.maktab.homeservice.repository.VerificationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class VerificationTokenServiceImplTest {


    private VerificationTokenRepository repository;
    private VerificationTokenServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(VerificationTokenRepository.class);
        service = new VerificationTokenServiceImpl(repository);
    }

    @Test
    void testFindByToken_shouldReturnToken_whenTokenExists() {
        // Arrange
        String tokenStr = "123456";
        VerificationToken token = new VerificationToken();
        token.setToken(tokenStr);

        when(repository.findByToken(tokenStr)).thenReturn(Optional.of(token));

        // Act
        Optional<VerificationToken> result = service.findByToken(tokenStr);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(tokenStr, result.get().getToken());
        verify(repository, times(1)).findByToken(tokenStr);
    }

    @Test
    void testFindByToken_shouldReturnEmpty_whenTokenDoesNotExist() {
        // Arrange
        String tokenStr = "not_exist_token";
        when(repository.findByToken(tokenStr)).thenReturn(Optional.empty());

        // Act
        Optional<VerificationToken> result = service.findByToken(tokenStr);

        // Assert
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByToken(tokenStr);
    }
}
