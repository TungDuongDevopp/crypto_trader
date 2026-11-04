package com.coin.cryptocurrency_trading_management.Service;

import com.coin.cryptocurrency_trading_management.DTO.User.UserResponse;
import com.coin.cryptocurrency_trading_management.Repository.Interface.UserRepository;
import com.coin.cryptocurrency_trading_management.Service.Interface.UserService;
import com.coin.cryptocurrency_trading_management.Util.Mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public List<UserResponse> getAll() throws IOException {
        var users = userRepository.getAll();
        return userMapper.toResponseList(users);
    }
}
