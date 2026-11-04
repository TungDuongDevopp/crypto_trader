package com.coin.cryptocurrency_trading_management.Util.Mapper;

import com.coin.cryptocurrency_trading_management.DTO.User.UserResponse;
import com.coin.cryptocurrency_trading_management.Entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = false))
public interface UserMapper {
    UserResponse toResponse(User user);
    List<UserResponse> toResponseList(List<User> users);
}
