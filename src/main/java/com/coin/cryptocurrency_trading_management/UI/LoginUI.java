package com.coin.cryptocurrency_trading_management.UI;

import com.coin.cryptocurrency_trading_management.Controller.AuthController;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import com.coin.cryptocurrency_trading_management.Util.Exception.BusinessException;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class LoginUI {

    private final AuthController authController;
    private final Scanner scanner;  // inject bean dùng chung

    public boolean printLoginForm() {
        String username;
        String password;
        var success = false;
        while (!success) {
            System.out.println("==============================");
            System.out.println("            LOGIN");
            System.out.println("==============================");
            System.out.print("Username: ");
            username = scanner.nextLine().trim();
            System.out.print("Password: ");
            password = scanner.nextLine(); // không trim password
            System.out.println("==============================");
           success =  submitForm(username, password);
        }
        return success;
    }

    private boolean submitForm(String username, String password) {
        System.out.print(ConsoleColor.YELLOW + "Submitting" + ConsoleColor.RESET);

        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(400);
                System.out.print(ConsoleColor.YELLOW + "." + ConsoleColor.RESET);
            }

            System.out.println();
            authController.login(username, password);
            System.out.println(
                    ConsoleColor.GREEN + ConsoleColor.BOLD
                            + "Login successful!"
                            + ConsoleColor.RESET
            );
            return true;

        } catch (BusinessException | ResourceNotFoundException e) {
            System.out.println(
                    ConsoleColor.RED + "Error: " + e.getMessage()
                            + ConsoleColor.RESET
            );

        } catch (IOException e) {
            System.out.println(
                    ConsoleColor.RED + "System error: Cannot access data files."
                            + ConsoleColor.RESET
            );

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(ConsoleColor.RED + "Login interrupted." + ConsoleColor.RESET);
        }
        return false;
    }
}
