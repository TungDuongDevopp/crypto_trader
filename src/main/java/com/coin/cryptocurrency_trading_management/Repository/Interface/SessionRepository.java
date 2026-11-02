package com.coin.cryptocurrency_trading_management.Repository.Interface;

import com.coin.cryptocurrency_trading_management.Entity.Session;

import java.io.IOException;

public interface SessionRepository extends FileRepository<Session> {

    Session getSessionActiveByUserId(long userId) throws IOException;

    void saveCurrentSession(long sessionId) throws IOException;

    Session getCurrentSession() throws IOException;

    void deleteCurrentSession() throws IOException;
}
