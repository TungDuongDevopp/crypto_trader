package com.coin.cryptocurrency_trading_management.Repository.Interface;

import com.coin.cryptocurrency_trading_management.Entity.Identifiable;

import java.io.IOException;
import java.util.List;

public interface FileRepository<T extends Identifiable> {
    void save(T content) throws IOException;

    List<T> getAll() throws IOException;

    T getById(long id) throws IOException;

    void deleteById(long id) throws IOException;

    T updateById(long id, T content) throws IOException;
}