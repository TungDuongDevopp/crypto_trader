package com.coin.cryptocurrency_trading_management.Controller;

import com.coin.cryptocurrency_trading_management.DTO.DashboardDTO;
import com.coin.cryptocurrency_trading_management.DTO.HomeDTO;
import com.coin.cryptocurrency_trading_management.Service.DashboardService;
import com.coin.cryptocurrency_trading_management.Service.Interface.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardDTO getInfo() throws IOException {
        return dashboardService.getDashboardInfo();
    }
    public void logout() throws IOException {
        dashboardService.logout();
    }
}
