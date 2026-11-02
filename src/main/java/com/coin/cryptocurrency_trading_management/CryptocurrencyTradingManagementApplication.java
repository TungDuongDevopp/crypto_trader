package com.coin.cryptocurrency_trading_management;

import com.coin.cryptocurrency_trading_management.UI.MainMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class CryptocurrencyTradingManagementApplication implements CommandLineRunner {

    private final MainMenu mainMenu;
    private final Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(CryptocurrencyTradingManagementApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            return;
        }
        mainMenu.display();
    }
}
