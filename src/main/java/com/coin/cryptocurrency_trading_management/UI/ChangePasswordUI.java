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
public class ChangePasswordUI {

    private final AuthController authController;
    private final Scanner scanner;  // inject bean dùng chung
    public boolean printChangePasswordForm() {
        String oldPassword;
        String newPassword;
        var success = false;
        while (!success) {
            System.out.println("==============================");
            System.out.println("            CHANGE PASSWORD");
            System.out.println("==============================");
            System.out.print("Old Password: ");
            oldPassword = scanner.nextLine().trim();
            System.out.print("New Password: ");
            newPassword = scanner.nextLine(); // không trim password
            System.out.println("==============================");
            success =  submitForm(oldPassword, newPassword);
        }
        return success;
    }
    private boolean submitForm(String oldPassword, String newPassword) {
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(400);
                System.out.print(ConsoleColor.YELLOW + "." + ConsoleColor.RESET);
            }

            System.out.println();
            authController.changePassword(oldPassword, newPassword);
            System.out.println(
                    ConsoleColor.GREEN + ConsoleColor.BOLD
                            + "Change password successful!"
                            + ConsoleColor.RESET
            );
            return true;
        }
        catch (BusinessException | ResourceNotFoundException e) {
            System.out.println(
                    ConsoleColor.RED + "Error: " + e.getMessage()
                            + ConsoleColor.RESET
            );

        }
        catch (IOException e) {
            System.out.println(
                    ConsoleColor.RED + "System error: Cannot access data files."
                            + ConsoleColor.RESET
            );
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(ConsoleColor.RED + "Login interrupted." + ConsoleColor.RESET);
        }
        return false;
    }
}
