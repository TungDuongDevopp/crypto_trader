package com.coin.cryptocurrency_trading_management.UI;

import com.coin.cryptocurrency_trading_management.Controller.AuthController;
import com.coin.cryptocurrency_trading_management.Entity.Enum.Role;
import com.coin.cryptocurrency_trading_management.UI.Admin.DashboardUI;
import com.coin.cryptocurrency_trading_management.UI.User.HomeUI;
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
    private final HomeUI homeUI;
    private final DashboardUI dashboardUI;
    private final AuthController authController;

    public void display() {
        System.out.println("Welcome to the Cryptocurrency Trading Management System");
        Role role;
        try {
            role = authController.getCurrentRole();
            if (authController.hasActiveSession() && role == Role.User) {
                homeUI.printDashboard();
            }
            if (authController.hasActiveSession() && role == Role.Admin) {
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
                    try{
                        var logined = loginUI.printLoginForm();
                        if (logined && authController.getCurrentRole() == Role.User) {
                            homeUI.printDashboard();
                        }
                        if (logined && authController.getCurrentRole() == Role.Admin) {
                            dashboardUI.printDashboard();
                        }
                    }
                    catch (IOException e){
                        System.out.println(ConsoleColor.RED + "System error: Cannot access user data." + ConsoleColor.RESET);
                    }

                    break;
                case "2":
                    var registed = registerUI.printRegisterForm();
                    if (registed) {
                        var loginedAfterRegister = loginUI.printLoginForm();
                        if (loginedAfterRegister) {
                            homeUI.printDashboard();
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
