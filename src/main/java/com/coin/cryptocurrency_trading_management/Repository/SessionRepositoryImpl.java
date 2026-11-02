package com.coin.cryptocurrency_trading_management.Repository;

import com.coin.cryptocurrency_trading_management.Entity.Session;
import com.coin.cryptocurrency_trading_management.Repository.Interface.SessionRepository;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SessionRepositoryImpl extends FileRepositoryImpl<Session> implements SessionRepository {

    private final Path currentSessionPath;

    @Autowired
    public SessionRepositoryImpl(@Value("${app.data.dir}") String dataDir, ObjectMapper objectMapper) {
        super(dataDir, "sessions.json", Session.class, objectMapper);
        this.currentSessionPath = Path.of(dataDir, "current_session.json");
    }

    @Override
    public Session getSessionActiveByUserId(long userId) throws IOException {
        return getAll().stream().filter(session -> session.getUserId() == userId && session.isActive()
        && LocalDateTime.now().isBefore(session.getExpireTime())
        ).findFirst().orElse(null);
    }

    @Override
    public void saveCurrentSession(long sessionId) throws IOException {
        if (currentSessionPath.getParent() != null) {
            Files.createDirectories(currentSessionPath.getParent());
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(currentSessionPath.toFile(), sessionId);
    }

    @Override
    public Session getCurrentSession() throws IOException {
        if (!Files.exists(currentSessionPath)) {
            return null;
        }
        String raw = Files.readString(currentSessionPath).trim();
        if (raw.isEmpty()) {
            return null;
        }
        try {
            long sessionId = Long.parseLong(raw);
            return getById(sessionId);
        } catch (NumberFormatException e) {
            if (raw.startsWith("[")) {
                JavaType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Session.class);
                List<Session> list = objectMapper.readValue(raw, listType);
                return list.isEmpty() ? null : list.getFirst();
            }
            return objectMapper.readValue(raw, Session.class);
        }
    }

    @Override
    public void deleteCurrentSession() throws IOException {
        Files.deleteIfExists(currentSessionPath);
    }
}
