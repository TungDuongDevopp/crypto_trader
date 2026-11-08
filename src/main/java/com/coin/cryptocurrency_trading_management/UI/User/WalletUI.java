package com.coin.cryptocurrency_trading_management.UI.User;

import com.coin.cryptocurrency_trading_management.Controller.WalletController;
import com.coin.cryptocurrency_trading_management.DTO.Wallet.UserWalletResponse;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class WalletUI {

    private final WalletController walletController;
    private final Scanner scanner;

    public void printWallet() {
        while (true) {
            UserWalletResponse wallet = walletController.getWalletInfo();
            if (wallet == null) {
                System.out.println(ConsoleColor.RED + "Cannot retrieve wallet information." + ConsoleColor.RESET);
                return;
            }

            BigDecimal balance = wallet.getBalance() != null ? wallet.getBalance() : BigDecimal.ZERO;
            BigDecimal totalDeposited = wallet.getTotalDeposited() != null ? wallet.getTotalDeposited() : BigDecimal.ZERO;
            BigDecimal totalWithdrawn = wallet.getTotalWithdrawn() != null ? wallet.getTotalWithdrawn() : BigDecimal.ZERO;

            System.out.printf("""
                ========================================
                                 WALLET
                ========================================
                
                Wallet Address:
                %s
                
                ----------------------------------------
                Wallet Status: %s
                Currency: %s
                
                Available Balance:       $%.2f
                Total Deposited:         $%.2f
                Total Withdrawn:         $%.2f
                
                ----------------------------------------
                WALLET MENU
                
                1. Deposit
                2. Withdraw
                3. Transfer to Holding
                4. Transaction History
                0. Back to Dashboard
                
                ========================================
                Select an option:\s""",
                    wallet.getAddress(),
                    wallet.getStatus(),
                    wallet.getCurrency(),
                    balance,
                    totalDeposited,
                    totalWithdrawn
            );
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "0":
                    return;
                default:
                    System.out.println("Feature is being updated. Please try again later.");
            }
        }
    }
}
