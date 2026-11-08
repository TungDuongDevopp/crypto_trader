package com.coin.cryptocurrency_trading_management.Service;

import com.coin.cryptocurrency_trading_management.DTO.Session.SessionResponse;
import com.coin.cryptocurrency_trading_management.Repository.Interface.SessionRepository;
import com.coin.cryptocurrency_trading_management.Service.Interface.SessionService;
import com.coin.cryptocurrency_trading_management.Util.Mapper.SessionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    @Override
    public List<SessionResponse> getAll() throws IOException {
        var sessions = sessionRepository.getAll();
        return sessionMapper.toResponseList(sessions);
    }
}
