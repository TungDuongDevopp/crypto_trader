package com.coin.cryptocurrency_trading_management;

import com.coin.cryptocurrency_trading_management.Config.JacksonConfig;
import com.coin.cryptocurrency_trading_management.Entity.Session;
import com.coin.cryptocurrency_trading_management.Entity.User;
import com.coin.cryptocurrency_trading_management.Repository.Interface.WalletRepository;
import com.coin.cryptocurrency_trading_management.Repository.SessionRepositoryImpl;
import com.coin.cryptocurrency_trading_management.Repository.UserRepositoryImpl;
import com.coin.cryptocurrency_trading_management.Repository.WalletRepositoryImpl;
import com.coin.cryptocurrency_trading_management.Service.AuthServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CurrentSessionTest {

    private AuthServiceImpl authService;
    private UserRepositoryImpl userRepository;
    private SessionRepositoryImpl sessionRepository;
    private Path currentSessionPath;
    private final String testDataDir = "Data";
    private WalletRepositoryImpl walletRepository;

    @BeforeEach
    void setUp() throws IOException {
        ObjectMapper objectMapper = new JacksonConfig().objectMapper();
        userRepository = new UserRepositoryImpl(testDataDir, objectMapper);
        sessionRepository = new SessionRepositoryImpl(testDataDir, objectMapper);
        authService = new AuthServiceImpl(userRepository, sessionRepository,walletRepository);
        currentSessionPath = Path.of(testDataDir, "current_session.json");

        sessionRepository.deleteCurrentSession();

        // Ensure test user exists
        if (userRepository.getUserByUsername("testuser01") == null) {
            User testUser = new User();
            testUser.setUsername("testuser01");
            testUser.setPassword("Password123@");
            userRepository.save(testUser);
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        if (sessionRepository != null) {
            sessionRepository.deleteCurrentSession();
        }
    }

    @Test
    void testLoginSavesCurrentSession() throws IOException {
        Session session = authService.login("testuser01", "Password123@");
        assertNotNull(session);
        assertTrue(Files.exists(currentSessionPath), "current_session.json must exist after login");

        Session current = sessionRepository.getCurrentSession();
        assertNotNull(current);
        assertEquals(session.getId(), current.getId());
        assertEquals(session.getUserId(), current.getUserId());
        assertTrue(current.isActive());
    }

    @Test
    void testActiveSessionDetection() throws IOException {
        Session session = authService.login("testuser01", "Password123@");
        assertNotNull(session);

        Session activeSession = authService.getActiveCurrentSession();
        assertNotNull(activeSession);
        assertEquals(session.getId(), activeSession.getId());

        // Now simulate session expiry
        session.setExpireTime(LocalDateTime.now().minusMinutes(5));
        sessionRepository.updateById(session.getId(), session);

        Session expiredSession = authService.getActiveCurrentSession();
        assertNull(expiredSession, "Expired session should not be active");
        assertFalse(Files.exists(currentSessionPath), "current_session.json should be deleted when expired");
    }

    @Test
    void testLogoutDeactivatesSessionAndDeletesCurrentSession() throws IOException {
        Session session = authService.login("testuser01", "Password123@");
        assertNotNull(session);
        assertTrue(Files.exists(currentSessionPath));

        authService.logout();

        // Check session in repository is inactive
        Session updated = sessionRepository.getById(session.getId());
        assertNotNull(updated);
        assertFalse(updated.isActive(), "Session status should be inactive after logout");

        // Check current_session is deleted
        assertFalse(Files.exists(currentSessionPath), "current_session.json must be deleted after logout");
        assertNull(sessionRepository.getCurrentSession());
    }
}
