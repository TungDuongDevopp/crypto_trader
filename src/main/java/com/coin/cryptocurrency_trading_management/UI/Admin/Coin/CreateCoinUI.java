package com.coin.cryptocurrency_trading_management.UI.Admin.Coin;

import com.coin.cryptocurrency_trading_management.Controller.CoinController;
import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinRequestCreate;
import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinCurrency;
import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinSymbol;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class CreateCoinUI {
    private final CoinController coinController;
    private final Scanner scanner;

    public void printCreateForm() {
        System.out.println(ConsoleColor.CYAN + "==============================");
        System.out.println("         CREATE COIN");
        System.out.println("==============================" + ConsoleColor.RESET);

        System.out.print("Coin Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Name cannot be empty!" + ConsoleColor.RESET);
            return;
        }

        CoinSymbol symbol = chooseCoinSymbol();
        CoinCurrency currency = chooseCoinCurrency();
        BigDecimal price = inputPrice();

        System.out.print("Coin Description: ");
        String description = scanner.nextLine().trim();

        CoinRequestCreate request = new CoinRequestCreate();
        request.setName(name);
        request.setSymbol(symbol);
        request.setCurrency(currency);
        request.setPrice(price);
        request.setDescription(description);

        try {
            coinController.createCoin(request);
            System.out.println(ConsoleColor.GREEN + "\n✓ Coin created successfully!" + ConsoleColor.RESET);
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "\n✗ Failed to create coin: " + e.getMessage() + ConsoleColor.RESET);
        } catch (Exception e) {
            System.out.println(ConsoleColor.RED + "\n✗ Error: " + e.getMessage() + ConsoleColor.RESET);
        }
    }

    private CoinSymbol chooseCoinSymbol() {
        System.out.println("\nSelect Coin Symbol:");
        for (CoinSymbol s : CoinSymbol.values()) {
            System.out.printf("  [%d] %s%n", s.getValue(), s.name());
        }
        while (true) {
            System.out.print("Choose symbol number: ");
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return CoinSymbol.fromValue(value);
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColor.RED + "Please enter a valid number!" + ConsoleColor.RESET);
            } catch (IllegalArgumentException e) {
                System.out.println(ConsoleColor.RED + e.getMessage() + "! Try again." + ConsoleColor.RESET);
            }
        }
    }

    private CoinCurrency chooseCoinCurrency() {
        System.out.println("\nSelect Coin Currency:");
        for (CoinCurrency c : CoinCurrency.values()) {
            System.out.printf("  [%d] %s%n", c.getValue(), c.name());
        }
        while (true) {
            System.out.print("Choose currency number: ");
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return CoinCurrency.fromValue(value);
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColor.RED + "Please enter a valid number!" + ConsoleColor.RESET);
            } catch (IllegalArgumentException e) {
                System.out.println(ConsoleColor.RED + e.getMessage() + "! Try again." + ConsoleColor.RESET);
            }
        }
    }

    private BigDecimal inputPrice() {
        while (true) {
            System.out.print("Coin Price: ");
            try {
                BigDecimal price = new BigDecimal(scanner.nextLine().trim());
                if (price.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println(ConsoleColor.RED + "Price must be greater than 0!" + ConsoleColor.RESET);
                    continue;
                }
                return price;
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColor.RED + "Invalid number format for price!" + ConsoleColor.RESET);
            }
        }
    }
}
