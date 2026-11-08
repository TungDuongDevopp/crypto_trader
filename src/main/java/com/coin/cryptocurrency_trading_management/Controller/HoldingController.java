package com.coin.cryptocurrency_trading_management.Controller;

import com.coin.cryptocurrency_trading_management.DTO.Holding.HoldingResponse;
import com.coin.cryptocurrency_trading_management.DTO.User.UserResponse;
import com.coin.cryptocurrency_trading_management.Entity.User;
import com.coin.cryptocurrency_trading_management.Service.Interface.AuthService;
import com.coin.cryptocurrency_trading_management.Service.Interface.HoldingService;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HoldingController {
    private final HoldingService holdingService;
    private final AuthService authService;

    public List<HoldingResponse> getAllHoldings() throws IOException {
        return  holdingService.getAll();
    }

    public List<HoldingResponse> getHoldingsByUserId() throws IOException {
        User user = authService.getCurrentUser();
        if (user == null) {
            throw new ResourceNotFoundException("User not logged in");
        }
        return holdingService.getHoldingsByUserId(user.getId());
    }

    public HoldingResponse getHoldingByUserIdAndCoinId(long coinId) throws IOException {
        User user = authService.getCurrentUser();
        if (user == null) {
            throw new ResourceNotFoundException("User not logged in");
        }
        return holdingService.getHoldingByUserIdAndCoinId(user.getId(), coinId);
    }
}
