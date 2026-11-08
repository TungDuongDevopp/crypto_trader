package com.coin.cryptocurrency_trading_management.UI.User;

import com.coin.cryptocurrency_trading_management.Controller.HoldingController;
import com.coin.cryptocurrency_trading_management.DTO.Holding.HoldingResponse;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class HoldingUI {
    private final HoldingController holdingController;
    private final Scanner scanner;
    public void show() {
        while (true) {
            printTableHolding();
            System.out.println(ConsoleColor.BOLD + "  Options:" + ConsoleColor.RESET);
            System.out.println("    0. Back to Portfolio");
            System.out.print("  Choose: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "0" -> { return; }
                default  -> System.out.println(ConsoleColor.YELLOW + "  Invalid choice, please try again." + ConsoleColor.RESET);
            }
        }
    }
    private void printTableHolding() {
        List<HoldingResponse> holdings;
        try {
            holdings = holdingController.getHoldingsByUserId();
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "  Can't get holding list. Please try again." + ConsoleColor.RESET);
            return;
        }

        // Cột: ID (4) | COIN ID (7) | QUANTITY (12) | AVG BUY (13) | CURRENT (13) | TOTAL VALUE (13) | PNL ($) (14) | PNL (%) (10)
        String separator = "+------+---------+--------------+---------------+---------------+---------------+----------------+------------+";
        String headerFmt = "| %-4s | %-7s | %-12s | %-13s | %-13s | %-13s | %-14s | %-10s |";
        String rowFmt    = "| %-4d | %-7d | %12s | %13s | %13s | %13s | %s | %s |";
        System.out.println();
        System.out.println(ConsoleColor.CYAN + ConsoleColor.BOLD
                + "                                                  MY HOLDING" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);
        System.out.printf(ConsoleColor.BOLD + headerFmt + ConsoleColor.RESET + "%n",
                "ID", "COIN ID", "QUANTITY", "AVG BUY", "CURRENT", "TOTAL VALUE", "PNL ($)", "PNL (%)");
        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);

        if (holdings == null || holdings.isEmpty()) {
            System.out.println(ConsoleColor.YELLOW + "  No holdings found." + ConsoleColor.RESET);
        } else {
            for (HoldingResponse holding : holdings) {
                BigDecimal qty = holding.getQuantity() != null ? holding.getQuantity() : BigDecimal.ZERO;
                BigDecimal avgBuyPrice = holding.getAverageBuyPrice() != null ? holding.getAverageBuyPrice() : BigDecimal.ZERO;
                BigDecimal currentPrice = holding.getCurrentPrice() != null ? holding.getCurrentPrice() : BigDecimal.ZERO;
                BigDecimal totalPrice = holding.getTotalPrice() != null ? holding.getTotalPrice() : BigDecimal.ZERO;
                BigDecimal pnl = holding.getPnl() != null ? holding.getPnl() : BigDecimal.ZERO;
                BigDecimal pnlPercentage = holding.getPnlPercentage() != null ? holding.getPnlPercentage() : BigDecimal.ZERO;

                String qtyStr = String.format("%.6f", qty);
                String avgBuyPriceStr = "$" + String.format("%.2f", avgBuyPrice);
                String currentPriceStr = "$" + String.format("%.2f", currentPrice);
                String totalPriceStr = "$" + String.format("%.2f", totalPrice);

                // Tô màu xanh nếu lãi, đỏ nếu lỗ
                String pnlColor = pnl.compareTo(BigDecimal.ZERO) >= 0 ? ConsoleColor.GREEN : ConsoleColor.RED;
                String pnlPrefix = pnl.compareTo(BigDecimal.ZERO) >= 0 ? "+$" : "-$";
                String pnlStr = pnlPrefix + String.format("%.2f", pnl.abs());
                String coloredPnl = pnlColor + String.format("%14s", pnlStr) + ConsoleColor.RESET;

                String pnlPctPrefix = pnlPercentage.compareTo(BigDecimal.ZERO) >= 0 ? "+" : "";
                String pnlPctStr = pnlPctPrefix + String.format("%.2f%%", pnlPercentage);
                String coloredPnlPct = pnlColor + String.format("%10s", pnlPctStr) + ConsoleColor.RESET;

                System.out.printf(rowFmt + "%n",
                        holding.getId(),
                        holding.getCoinId(),
                        qtyStr,
                        avgBuyPriceStr,
                        currentPriceStr,
                        totalPriceStr,
                        coloredPnl,
                        coloredPnlPct);
            }
        }

        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);
        System.out.println(ConsoleColor.GRAY
                + "  Total: " + (holdings != null ? holdings.size() : 0) + " holding record(s)"
                + ConsoleColor.RESET);
        System.out.println();
    }
}
