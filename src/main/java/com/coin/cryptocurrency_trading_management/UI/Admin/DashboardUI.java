package com.coin.cryptocurrency_trading_management.UI.Admin;

import com.coin.cryptocurrency_trading_management.Controller.DashboardController;
import com.coin.cryptocurrency_trading_management.DTO.DashboardDTO;
import com.coin.cryptocurrency_trading_management.UI.Admin.Coin.AdminCoinUI;
import com.coin.cryptocurrency_trading_management.UI.ChangePasswordUI;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import com.coin.cryptocurrency_trading_management.Util.Exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class DashboardUI {
    private final ChangePasswordUI changePasswordUI;
    private final Scanner scanner;
    private final DashboardController dashboardController;
    private final AdminUserUI adminUserUI;
    private final AdminCoinUI adminCoinUI;
    private final AdminSessionUI adminSessionUI;
    private final AdminWalletUI adminWalletUI;
    private final AdminHoldingUI adminHoldingUI;

    public void printDashboard() {
        DashboardDTO dashboard;
        try {
            dashboard = dashboardController.getInfo();
        } catch (BusinessException e) {
            System.out.println(
                    ConsoleColor.RED
                            + "Can't get user. Please try again."
                            + ConsoleColor.RESET
            );
            return;
        } catch (IOException e) {
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
                    Total User:   %d
                    Total Coin:  %d
                    Total Session:     %d
                    Total Wallet:    %d
                    
                    ----------------------------------------
                    1. User Management
                    2. Coin Management
                    3. Session Management
                    4. Wallet Management
                    5. Holding Management
                    6. Change Password
                    7. Logout
                    
                    ========================================
                    Choose an option:\s""",
                    dashboard.getUsername(),
                    dashboard.getNumberOfUser(),
                    dashboard.getNumberOfCoin(),
                    dashboard.getNumberOfSession(),
                    dashboard.getNumberOfWallet()
            );
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> adminUserUI.show();
                case "2" -> adminCoinUI.show();
                case "3" -> adminSessionUI.show();
                case "4" -> adminWalletUI.show();
                case "5" -> adminHoldingUI.show();
                case "6" -> changePasswordUI.printChangePasswordForm();
                case "7" -> {
                    logout();
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void logout() {
        try {
            dashboardController.logout();
            System.out.println(
                    ConsoleColor.GREEN + ConsoleColor.BOLD
                            + "Logged out successfully!"
                            + ConsoleColor.RESET
            );
        } catch (IOException e) {
            System.out.println(
                    ConsoleColor.RED
                            + "Logout failed!"
                            + ConsoleColor.RESET
            );
        }
    }
}
