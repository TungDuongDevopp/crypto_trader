package com.coin.cryptocurrency_trading_management.Service.Interface;

import com.coin.cryptocurrency_trading_management.DTO.Session.SessionResponse;

import java.io.IOException;
import java.util.List;

public interface SessionService {
    List<SessionResponse> getAll() throws IOException;
}
