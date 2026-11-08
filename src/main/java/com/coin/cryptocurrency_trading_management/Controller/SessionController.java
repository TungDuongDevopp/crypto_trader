package com.coin.cryptocurrency_trading_management.Controller;

import com.coin.cryptocurrency_trading_management.DTO.Session.SessionResponse;
import com.coin.cryptocurrency_trading_management.Service.Interface.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    public List<SessionResponse> getAllSessions() throws IOException {
       return sessionService.getAll();
    }
}
