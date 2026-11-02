package com.coin.cryptocurrency_trading_management.UI;

import com.coin.cryptocurrency_trading_management.Controller.AuthController;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class MainMenu {

    private final Scanner scanner;  // inject bean dùng chung
    private final LoginUI loginUI;
    private final RegisterUI registerUI;
    private final DashboardUI dashboardUI;
    private final AuthController authController;

    public void display() {
        System.out.println("Welcome to the Cryptocurrency Trading Management System");

        try {
            if (authController.hasActiveSession()) {
                dashboardUI.printDashboard();
            }
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "System error: Cannot access session data." + ConsoleColor.RESET);
        }

        while (true) {
            System.out.println("==============================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    var logined = loginUI.printLoginForm();
                    if (logined) {
                        dashboardUI.printDashboard();
                    }
                    break;
                case "2":
                    var registed = registerUI.printRegisterForm();
                    if (registed) {
                        var loginedAfterRegister = loginUI.printLoginForm();
                        if (loginedAfterRegister) {
                            dashboardUI.printDashboard();
                        }
                    }
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
