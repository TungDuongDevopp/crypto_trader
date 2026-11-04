package com.coin.cryptocurrency_trading_management.Controller;

import com.coin.cryptocurrency_trading_management.DTO.HomeDTO;
import com.coin.cryptocurrency_trading_management.Service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import java.io.IOException;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    public HomeDTO getInfo() throws IOException {
         return homeService.getDashboardInfo();
    }
    public void logout() throws IOException {
            homeService.logout();
    }
}
