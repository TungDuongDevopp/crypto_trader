package com.coin.cryptocurrency_trading_management.UI;

import com.coin.cryptocurrency_trading_management.Controller.DashboardController;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class DashboardUI {

    private final DashboardController dashboardController;
    private final ChangePasswordUI changePasswordUI;
    private final Scanner scanner;

    public void printDashboard() {
        while (true) {
            String username = dashboardController.getUserName();

            System.out.printf("""
                    ========================================
                                  DASHBOARD
                    ========================================
                    
                    Welcome, %s!
                    
                    Wallet Balance: $300.00
                    Portfolio Value: $0.00
                    Total Assets: $300.00
                    
                    ----------------------------------------
                    1. View Market
                    2. AI Analysis
                    3. Buy Coin
                    4. Sell Coin
                    5. My Portfolio
                    6. Wallet
                    7. Change Password
                    8. Logout
                    
                    ========================================
                    Choose an option:\s""", username);
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "7":
                    changePasswordUI.printChangePasswordForm();
                    break;
                case "8":
                    if (dashboardController.logout()) {
                        System.out.println(
                                ConsoleColor.GREEN + ConsoleColor.BOLD
                                        + "Logged out successfully!"
                                        + ConsoleColor.RESET
                        );
                    } else {
                        System.out.println(
                                ConsoleColor.RED
                                        + "Logout failed!"
                                        + ConsoleColor.RESET
                        );
                    }
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
