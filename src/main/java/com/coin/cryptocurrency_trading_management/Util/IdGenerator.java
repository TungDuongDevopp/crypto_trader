package com.coin.cryptocurrency_trading_management.Util;

import com.coin.cryptocurrency_trading_management.Entity.Identifiable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IdGenerator {
    public static synchronized long generateId(Path sequencePath)
            throws IOException {

        Files.createDirectories(sequencePath.getParent());

        long currentId = 0;

        if (Files.exists(sequencePath)) {
            String value = Files.readString(sequencePath).trim();

            if (!value.isEmpty()) {
                currentId = Long.parseLong(value);
            }
        }
        long nextId = currentId + 1;
        Files.writeString(sequencePath, String.valueOf(nextId));

        return nextId;
    }
}
