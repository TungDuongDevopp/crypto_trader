package com.coin.cryptocurrency_trading_management.UI;

import com.coin.cryptocurrency_trading_management.Controller.AuthController;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import com.coin.cryptocurrency_trading_management.Util.Exception.BusinessException;
import com.coin.cryptocurrency_trading_management.Util.Exception.DuplicateResourceException;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;
@RequiredArgsConstructor
@Component
public class RegisterUI {
    private final AuthController authController;
    private final Scanner scanner;  // inject bean dùng chung

    public boolean printRegisterForm() {
        String username, password,confirmPassword;
        var success = false;
        while(!success) {
            System.out.println("==============================");
            System.out.println("            REGISTER");
            System.out.println("==============================");
            System.out.print("Username: ");
            username = scanner.nextLine().trim();
            System.out.print("Password: ");
            password = scanner.nextLine();
            System.out.print("Confirm password: ");
            confirmPassword = scanner.nextLine();
            System.out.println("==============================");
            success = submitForm(username, password,confirmPassword);
        }
        return success;

    }
    private boolean submitForm(String username, String password,String confirmPassword) {
        System.out.print(ConsoleColor.YELLOW + "Submitting" + ConsoleColor.RESET);

        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(400);
                System.out.print(ConsoleColor.YELLOW + "." + ConsoleColor.RESET);
            }
            System.out.println();
            authController.register(username,password,confirmPassword);
            System.out.println(
                    ConsoleColor.GREEN + ConsoleColor.BOLD
                            + "Register successful!"
                            + ConsoleColor.RESET
            );
            return true;
        } catch (BusinessException | ResourceNotFoundException | DuplicateResourceException e) {
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
