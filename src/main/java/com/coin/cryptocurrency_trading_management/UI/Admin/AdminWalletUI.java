package com.coin.cryptocurrency_trading_management.UI.Admin;

import com.coin.cryptocurrency_trading_management.Controller.WalletController;
import com.coin.cryptocurrency_trading_management.DTO.Wallet.AdminWalletResponse;
import com.coin.cryptocurrency_trading_management.DTO.Wallet.WalletChangeStatusRequest;
import com.coin.cryptocurrency_trading_management.Entity.Enum.WalletStatus;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import com.coin.cryptocurrency_trading_management.Util.Exception.CanMaltipulateDataException;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class AdminWalletUI {
    private final WalletController walletController;
    private final Scanner scanner;

    public void show() {
        while (true) {
            printTableWallet();
            System.out.println(ConsoleColor.BOLD + "  Options:" + ConsoleColor.RESET);
            System.out.println("    1. Change wallet status");
            System.out.println("    0. Back to Dashboard");
            System.out.print("  Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> changeStatus();
                case "0" -> { return; }
                default  -> System.out.println(ConsoleColor.YELLOW + "  Invalid choice, please try again." + ConsoleColor.RESET);
            }
        }
    }

    private void printTableWallet() {
        List<AdminWalletResponse> wallets;
        try {
            wallets = walletController.getWallets();
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "  Can't get wallet list. Please try again." + ConsoleColor.RESET);
            return;
        }

        // Cột: ID (4) | USER ID (7) | ADDRESS (39) | CURRENCY (8) | BALANCE (14) | STATUS (10)
        String separator = "+------+---------+-----------------------------------------+----------+----------------+------------+";
        String headerFmt = "| %-4s | %-7s | %-39s | %-8s | %-14s | %-10s |";
        String rowFmt    = "| %-4d | %-7d | %-39s | %-8s | %14s | %s |";

        System.out.println();
        System.out.println(ConsoleColor.CYAN + ConsoleColor.BOLD
                + "                                       WALLET MANAGEMENT" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);
        System.out.printf(ConsoleColor.BOLD + headerFmt + ConsoleColor.RESET + "%n",
                "ID", "USER ID", "ADDRESS", "CURRENCY", "BALANCE", "STATUS");
        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);

        if (wallets == null || wallets.isEmpty()) {
            System.out.println(ConsoleColor.YELLOW + "  No wallets found." + ConsoleColor.RESET);
        } else {
            for (AdminWalletResponse wallet : wallets) {
                String statusColor = switch (wallet.getStatus()) {
                    case ACTIVE -> ConsoleColor.GREEN;
                    case FROZEN -> ConsoleColor.YELLOW;
                    case CLOSED -> ConsoleColor.RED;
                };

                // Căn lề chuỗi thuần 10 ký tự trước, sau đó mới bọc mã màu ra ngoài để không lệch bảng
                String formattedStatus = String.format("%-10s", wallet.getStatus());
                String coloredStatus = statusColor + formattedStatus + ConsoleColor.RESET;

                BigDecimal balance = wallet.getBalance() != null ? wallet.getBalance() : BigDecimal.ZERO;
                String balanceStr = String.format("%.2f", balance);

                System.out.printf(rowFmt + "%n",
                        wallet.getId(),
                        wallet.getUserId(),
                        wallet.getAddress(),
                        wallet.getCurrency(),
                        balanceStr,
                        coloredStatus);
            }
        }

        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);
        System.out.println(ConsoleColor.GRAY
                + "  Total: " + (wallets != null ? wallets.size() : 0) + " wallet(s)"
                + ConsoleColor.RESET);
        System.out.println();
    }

    private void changeStatus() {
        System.out.print("Enter Wallet ID: ");
        long id;
        try {
            id = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColor.RED + "Invalid ID number!" + ConsoleColor.RESET);
            return;
        }

        System.out.println("\nSelect Wallet Status:");
        for (WalletStatus s : WalletStatus.values()) {
            System.out.printf("  [%d] %s%n", s.getValue(), s.name());
        }

        while (true) {
            System.out.print("Choose status number: ");
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                WalletStatus status = WalletStatus.fromValue(value);

                var request = new WalletChangeStatusRequest();
                request.setId(id);
                request.setStatus(status);

                walletController.changeStatus(request);
                System.out.println(ConsoleColor.GREEN + "✓ Wallet status changed successfully!" + ConsoleColor.RESET);
                return;

            } catch (NumberFormatException e) {
                System.out.println(ConsoleColor.RED + "Please enter a valid number!" + ConsoleColor.RESET);
            } catch (IllegalArgumentException e) {
                System.out.println(ConsoleColor.RED + e.getMessage() + "! Try again." + ConsoleColor.RESET);
            } catch (ResourceNotFoundException | CanMaltipulateDataException e) {
                System.out.println(ConsoleColor.RED + "✗ Error: " + e.getMessage() + ConsoleColor.RESET);
                return;
            } catch (IOException e) {
                System.out.println(ConsoleColor.RED + "✗ System error: Cannot access wallet data." + ConsoleColor.RESET);
                return;
            }
        }
    }
}
