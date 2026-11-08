package com.coin.cryptocurrency_trading_management.UI.User;

import com.coin.cryptocurrency_trading_management.Controller.PortfolioController;
import com.coin.cryptocurrency_trading_management.DTO.Holding.HoldingResponse;
import com.coin.cryptocurrency_trading_management.DTO.PortfolioDTO;
import com.coin.cryptocurrency_trading_management.Controller.HoldingController;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class PortfolioUI {
    private final PortfolioController portfolioController;
    private final HoldingController holdingController;
    private final HoldingUI holdingUI;
    private final Scanner scanner;

    public void show() {
        while (true) {
            printPortfolioDetails();
            System.out.println(ConsoleColor.BOLD + "  Options:" + ConsoleColor.RESET);
            System.out.println("    1. View Holding Details");
            System.out.println("    2. View Transaction History");
            System.out.println("    3. View Order History");
            System.out.println("    0. Back to Dashboard");
            System.out.print("  Choose: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> holdingUI.show();
                case "0" -> { return; }
                default  -> System.out.println(ConsoleColor.YELLOW + "  Invalid choice, please try again." + ConsoleColor.RESET);
            }
        }
    }

    private void printPortfolioDetails() {
        // ── 1. Lấy Portfolio summary ──────────────────────────────────────
        PortfolioDTO portfolio = null;
        try {
            portfolio = portfolioController.getPortfolio();
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "  Can't load portfolio. Please try again." + ConsoleColor.RESET);
        }

        // ── 2. Lấy danh sách Holding ──────────────────────────────────────
        List<HoldingResponse> holdings = null;
        try {
            holdings = holdingController.getHoldingsByUserId();
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "  Can't get holding list. Please try again." + ConsoleColor.RESET);
        }

        // ── 3. Layout constants ───────────────────────────────────────────
        // Holding table width  = 65 chars  (+------+---------+--------------+---------------+---------------+)
        String separator  = "+------+---------+--------------+---------------+---------------+";
        String borderLine = "=================================================================";
        String headerFmt  = "| %-4s | %-7s | %-12s | %-13s | %-13s |";
        String rowFmt     = "| %-4d | %-7d | %12s | %13s | %13s |";

        System.out.println();
        System.out.println(ConsoleColor.CYAN + borderLine + ConsoleColor.RESET);
        System.out.println(ConsoleColor.BOLD + "                          MY PORTFOLIO" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.CYAN + borderLine + ConsoleColor.RESET);
        System.out.println();

        // ── 4. Portfolio summary ──────────────────────────────────────────
        if (portfolio != null) {
            BigDecimal totalAssets    = nvl(portfolio.getTotalAssets());
            BigDecimal walletBalance  = nvl(portfolio.getWalletBalance());
            BigDecimal investedValue  = nvl(portfolio.getInvestedValue());
            BigDecimal pnl            = nvl(portfolio.getUnrealizedPnl());
            BigDecimal pnlPct         = nvl(portfolio.getUnrealizedPnlPct());

            String pnlSign   = pnl.compareTo(BigDecimal.ZERO) >= 0 ? "+" : "";
            String pnlColor  = pnl.compareTo(BigDecimal.ZERO) >= 0 ? ConsoleColor.GREEN : ConsoleColor.RED;
            String pnlStr    = pnlSign + String.format("$%.2f", pnl)
                             + " (" + pnlSign + String.format("%.2f", pnlPct) + "%)";

            System.out.printf("  %-20s : $%.2f%n",  "Total Assets",   totalAssets);
            System.out.printf("  %-20s : $%.2f%n",  "Wallet Balance", walletBalance);
            System.out.printf("  %-20s : $%.2f%n",  "Invested Value", investedValue);
            System.out.print( "  Unrealized PnL       : ");
            System.out.println(pnlColor + pnlStr + ConsoleColor.RESET);
        } else {
            System.out.println("  " + ConsoleColor.YELLOW + "Portfolio data unavailable." + ConsoleColor.RESET);
        }

        System.out.println();
        System.out.println(ConsoleColor.CYAN + borderLine + ConsoleColor.RESET);
        System.out.println();

        // ── 5. Holding table ─────────────────────────────────────────────
        System.out.println(ConsoleColor.BOLD + "                           MY HOLDING" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);
        System.out.printf(ConsoleColor.BOLD + headerFmt + ConsoleColor.RESET + "%n",
                "ID", "COIN ID", "QUANTITY", "AVG BUY", "CURRENT");
        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);

        if (holdings == null || holdings.isEmpty()) {
            System.out.println(ConsoleColor.YELLOW + "  No holdings found." + ConsoleColor.RESET);
        } else {
            for (HoldingResponse h : holdings) {
                BigDecimal qty          = nvl(h.getQuantity());
                BigDecimal avgBuyPrice  = nvl(h.getAverageBuyPrice());
                BigDecimal currentPrice = nvl(h.getCurrentPrice());

                String qtyStr         = String.format("%-12s", String.format("%.6f", qty));
                String avgBuyPriceStr = String.format("%-13s", "$" + String.format("%.2f", avgBuyPrice));
                String currentPriceStr = String.format("%-13s", "$" + String.format("%.2f", currentPrice));

                System.out.printf(rowFmt + "%n",
                        h.getId(),
                        h.getCoinId(),
                        qtyStr,
                        avgBuyPriceStr,
                        currentPriceStr
                );
            }
        }

        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);
        System.out.println(ConsoleColor.GRAY
                + "  Total: " + (holdings != null ? holdings.size() : 0) + " holding record(s)"
                + ConsoleColor.RESET);
        System.out.println();
    }

    /** Null-safe helper: returns ZERO if value is null */
    private BigDecimal nvl(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }
}
