package com.coin.cryptocurrency_trading_management.UI.Admin;

import com.coin.cryptocurrency_trading_management.Controller.UserController;
import com.coin.cryptocurrency_trading_management.DTO.User.UserResponse;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class AdminUserUI {
    private final UserController userController;
    private final Scanner scanner;

    public void show() {
        while (true) {
            printTableUser();

            System.out.println(ConsoleColor.BOLD + "  Options:" + ConsoleColor.RESET);
            System.out.println("    0. Back to Dashboard");
            System.out.print("  Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "0" -> { return; }
                default  -> System.out.println(ConsoleColor.YELLOW + "  Feature coming soon." + ConsoleColor.RESET);
            }
        }
    }

    private void printTableUser() {
        List<UserResponse> users;
        try {
            users = userController.getUsers();
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "Can't get userList. Please try again." + ConsoleColor.RESET);
            return;
        }

        String separator = "+------+----------------------+---------------+";
        String header    = "| %-4s | %-20s | %-13s |";
        String row       = "| %-4d | %-20s | %-22s |";

        System.out.println();
        System.out.println(ConsoleColor.CYAN + ConsoleColor.BOLD
                + "              USER MANAGEMENT" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);
        System.out.printf(ConsoleColor.BOLD + header + ConsoleColor.RESET + "%n",
                "ID", "USERNAME", "ROLE");
        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);

        if (users == null || users.isEmpty()) {
            System.out.println(ConsoleColor.YELLOW + "| No users found." + ConsoleColor.RESET);
        } else {
            for (UserResponse user : users) {
                String roleColor = switch (user.getRole().name()) {
                    case "ADMIN" -> ConsoleColor.RED;
                    case "USER"  -> ConsoleColor.GREEN;
                    default      -> ConsoleColor.WHITE;
                };
                System.out.printf(row + "%n",
                        user.getId(),
                        user.getUsername(),
                        roleColor + user.getRole() + ConsoleColor.RESET);
            }
        }

        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);
        System.out.println(ConsoleColor.GRAY + "  Total: " + (users != null ? users.size() : 0) + " user(s)" + ConsoleColor.RESET);
        System.out.println();
    }
}
