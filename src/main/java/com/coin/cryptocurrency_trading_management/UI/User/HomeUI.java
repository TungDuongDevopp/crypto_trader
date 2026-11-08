package com.coin.cryptocurrency_trading_management.UI.User;

import com.coin.cryptocurrency_trading_management.Controller.HomeController;
import com.coin.cryptocurrency_trading_management.DTO.HomeDTO;
import com.coin.cryptocurrency_trading_management.UI.ChangePasswordUI;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import com.coin.cryptocurrency_trading_management.Util.Exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class HomeUI {

    private final HomeController homeController;
    private final ChangePasswordUI changePasswordUI;
    private final WalletUI walletUI;
    private final Scanner scanner;
    private final PortfolioUI portfolioUI;
    private final MarketUI marketUI;

    public void printDashboard() {
        HomeDTO dashboard;
        try{
            dashboard = homeController.getInfo();
        }
        catch (BusinessException e){
            System.out.println(
                    ConsoleColor.RED
                            + "Can't get user. Please try again."
                            + ConsoleColor.RESET
            );
            return;
        }
        catch (IOException e){
            System.out.println(
                    ConsoleColor.RED
                            + "Can't get dashboard info. Please try again."
                            + ConsoleColor.RESET
            );
            return;
        }
        while (true) {
            System.out.printf("""
                    ========================================
                                  DASHBOARD
                    ========================================
                    
                    Welcome, %s!
                    Wallet Balance:   $%.2f
                    Portfolio Value:  $%.2f
                    Total Assets:     $%.2f
                    
                    ----------------------------------------
                    1. View Market
                    2. AI Analysis
                    3. Wallet
                    4. Portfolio
                    5. Change Password
                    6. Logout
                    
                    ========================================
                    Choose an option:\s""",
                    dashboard.getUsername(),
                    dashboard.getWalletBalance(),
                    dashboard.getPortfolioValue(),
                    dashboard.getTotalAssets()
            );
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> marketUI.show();
                case "3" -> walletUI.printWallet();
                case "4" -> portfolioUI.show();
                case "5" -> changePasswordUI.printChangePasswordForm();
                case "6" -> {
                    logout();
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
    private void logout(){
        try{
            homeController.logout();
            System.out.println(
                    ConsoleColor.GREEN + ConsoleColor.BOLD
                            + "Logged out successfully!"
                            + ConsoleColor.RESET
            );
        }
        catch (IOException e){
            System.out.println(
                    ConsoleColor.RED
                            + "Logout failed!"
                            + ConsoleColor.RESET
            );
        }
    }
}
