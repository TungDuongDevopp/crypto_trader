package com.coin.cryptocurrency_trading_management.Util.Mapper;

import com.coin.cryptocurrency_trading_management.DTO.Session.SessionResponse;
import com.coin.cryptocurrency_trading_management.Entity.Session;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    SessionResponse toResponse(Session session);
    List<SessionResponse> toResponseList(List<Session> sessions);
}
