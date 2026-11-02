package com.coin.cryptocurrency_trading_management.Repository;

import com.coin.cryptocurrency_trading_management.Entity.Identifiable;
import com.coin.cryptocurrency_trading_management.Repository.Interface.FileRepository;
import com.coin.cryptocurrency_trading_management.Util.IdGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileRepositoryImpl<T extends Identifiable> implements FileRepository<T> {

    protected final Path filePath;
    protected final Class<T> entityClass;
    protected final ObjectMapper objectMapper;
    protected final Path sequencePath;

    public FileRepositoryImpl(String dataDir, String filename, Class<T> entityClass, ObjectMapper objectMapper) {
        this.filePath = Path.of(dataDir, filename);
        this.entityClass = entityClass;
        this.objectMapper = objectMapper;
        this.sequencePath = Path.of(dataDir, "sequence",
                filename.replace(".json", ".txt"));
    }

    @Override
    public void save(T content) throws IOException {
        List<T> data = getAll();
        content.setId(IdGenerator.generateId(sequencePath));
        data.add(content);
        writeAll(data);
    }

    @Override
    public List<T> getAll() throws IOException {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        // File rỗng hoặc chỉ chứa khoảng trắng → trả về list rỗng
        String raw = Files.readString(filePath).trim();
        if (raw.isEmpty()) {
            return new ArrayList<>();
        }

        JavaType listType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, entityClass);

        return objectMapper.readValue(raw, listType);
    }

    @Override
    public T getById(long id) throws IOException {
        return getAll().stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private void writeAll(List<T> data) throws IOException {
        Files.createDirectories(filePath.getParent());
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(filePath.toFile(), data);
    }

    @Override
    public void deleteById(long id) throws IOException {
        List<T> data = getAll();
        boolean removed = data.removeIf(item -> item.getId() == id);
        if (removed) {
            writeAll(data);
        }
    }

    @Override
    public T updateById(long id, T content) throws IOException {
        List<T> data = getAll();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == id) {
                content.setId(id); // đảm bảo ID không bị thay đổi khi update
                data.set(i, content);
                writeAll(data);
                return content;
            }
        }
        return null;
    }
}
