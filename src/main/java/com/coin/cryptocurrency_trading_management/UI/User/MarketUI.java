package com.coin.cryptocurrency_trading_management.UI.User;

import com.coin.cryptocurrency_trading_management.Controller.CoinController;
import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinResponse;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class MarketUI {
    private final CoinController coinController;
    private final Scanner scanner;
    private final TradeCoinUI tradeCoinUI;

    public void show() {
        while (true) {
            printTableCoin();

            System.out.println(ConsoleColor.BOLD + "  Options:" + ConsoleColor.RESET);
            System.out.println("    1. Buy Coin");
            System.out.println("    2. Sell Coin");
            System.out.println("    0. Back to Home");
            System.out.print("  Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> openBuyCoinUI();
                case "2" -> openSellCoinUI();
                case "0" -> { return; }
                default  -> System.out.println(ConsoleColor.YELLOW + "  Lựa chọn không hợp lệ. Vui lòng thử lại!" + ConsoleColor.RESET);
            }
        }
    }

    private Long getCoinIdInput() {
        System.out.print("  Nhập Coin ID (hoặc Enter để quay lại): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColor.RED + "  ID không hợp lệ!" + ConsoleColor.RESET);
            return null;
        }
    }

    private void openBuyCoinUI() {
        Long coinId = getCoinIdInput();
        if (coinId != null) {
            tradeCoinUI.buyCoinForm(coinId);
        }
    }

    private void openSellCoinUI() {
        Long coinId = getCoinIdInput();
        if (coinId != null) {
            tradeCoinUI.sellCoinForm(coinId);
        }
    }

    private void printTableCoin() {
        List<CoinResponse> coins;
        try {
            coins = coinController.getCoins();
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "  Can't get coin list. Please try again." + ConsoleColor.RESET);
            return;
        }

        String separator = "+------+------------------+--------+----------+-------------------+";
        String headerFmt = "| %-4s | %-16s | %-6s | %-8s | %-17s |";
        String rowFmt    = "| %-4d | %-16s | %-6s | %-8s | %17s |";

        System.out.println();
        System.out.println(ConsoleColor.CYAN + ConsoleColor.BOLD
                + "                    MARKET" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);
        System.out.printf(ConsoleColor.BOLD + headerFmt + ConsoleColor.RESET + "%n",
                "ID", "NAME", "SYMBOL", "CURRENCY", "PRICE");
        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);

        if (coins == null || coins.isEmpty()) {
            System.out.println(ConsoleColor.YELLOW + "  No coins found." + ConsoleColor.RESET);
        } else {
            for (CoinResponse coin : coins) {
                String priceStr = coin.getCurrency() + " " + coin.getPrice().toPlainString();

                System.out.printf(rowFmt + "%n",
                        coin.getId(),
                        coin.getName(),
                        coin.getSymbol(),
                        coin.getCurrency(),
                        priceStr);
            }
        }

        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);
        System.out.println(ConsoleColor.GRAY
                + "  Total: " + (coins != null ? coins.size() : 0) + " coin(s)"
                + ConsoleColor.RESET);
        System.out.println();
    }
}
