package com.coin.cryptocurrency_trading_management.UI.Admin.Coin;

import com.coin.cryptocurrency_trading_management.Controller.CoinController;
import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinChangeStatusRequest;
import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinResponse;
import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinStatus;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class AdminCoinUI {
    private final CoinController coinController;
    private final CreateCoinUI createCoinUI;
    private final UpdateCoinUI updateCoinUI;
    private final Scanner scanner;

    public void show() {
        while (true) {
            printTableCoin();

            System.out.println(ConsoleColor.BOLD + "  Options:" + ConsoleColor.RESET);
            System.out.println("    1. Create Coin");
            System.out.println("    2. Update Coin");
            System.out.println("    3. Update Price");
            System.out.println("    4. Change Status");
            System.out.println("    0. Back to Dashboard");
            System.out.print("  Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> createCoinUI.printCreateForm();
                case "2" -> updateCoin();
                case "4" ->changeStatus();
                case "0" -> { return; }
                default  -> System.out.println(ConsoleColor.YELLOW + "  Feature coming soon." + ConsoleColor.RESET);
            }
        }
    }

    private long getId() {
        System.out.print("ID: ");
        return scanner.nextLong();
    }
    private void updateCoin() {
       long id = getId();
       scanner.nextLine();
       updateCoinUI.printUpdateForm(id);
    }
    private void changeStatus() {
        long id = getId();
        scanner.nextLine();
        System.out.println("\nSelect Coin Status:");
        for (CoinStatus s : CoinStatus.values()) {
            System.out.printf("  [%d] %s%n", s.getValue(), s.name());
        }
        while (true) {
            System.out.print("Choose status number: ");
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                CoinStatus status = CoinStatus.fromValue(value);
                var request = new CoinChangeStatusRequest();
                request.setStatus(status);
                request.setId(id);
                coinController.updateCoinStatus(request);
                return;

            } catch (NumberFormatException e) {
                System.out.println(ConsoleColor.RED + "Please enter a valid number!" + ConsoleColor.RESET);
            } catch (IllegalArgumentException e) {
                System.out.println(ConsoleColor.RED + e.getMessage() + "! Try again." + ConsoleColor.RESET);
            } catch (ResourceNotFoundException e){
                System.out.println(ConsoleColor.RED + e.getMessage() + ConsoleColor.RESET);
                return;
            }
            catch (IOException e) {
                System.out.println(ConsoleColor.RED + e.getMessage() + "! Can't change status." + ConsoleColor.RESET);
                return;
            }

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

        String separator = "+------+------------------+--------+----------+-------------------+------------+";
        String headerFmt = "| %-4s | %-16s | %-6s | %-8s | %-17s | %-10s |";
        String rowFmt    = "| %-4d | %-16s | %-6s | %-8s | %17s | %s |";

        System.out.println();
        System.out.println(ConsoleColor.CYAN + ConsoleColor.BOLD
                + "                    COIN MANAGEMENT" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);
        System.out.printf(ConsoleColor.BOLD + headerFmt + ConsoleColor.RESET + "%n",
                "ID", "NAME", "SYMBOL", "CURRENCY", "PRICE", "STATUS");
        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);

        if (coins == null || coins.isEmpty()) {
            System.out.println(ConsoleColor.YELLOW + "  No coins found." + ConsoleColor.RESET);
        } else {
            for (CoinResponse coin : coins) {
                String statusColor = switch (coin.getStatus()) {
                    case ACTIVE   -> ConsoleColor.GREEN;
                    case INACTIVE -> ConsoleColor.YELLOW;
                    case DELISTED -> ConsoleColor.RED;
                };

                // Căn lề chuỗi thuần 10 ký tự trước, sau đó mới bọc mã màu ra ngoài
                String formattedStatus = String.format("%-10s", coin.getStatus());
                String coloredStatus = statusColor + formattedStatus + ConsoleColor.RESET;

                String priceStr = coin.getCurrency() + " " + coin.getPrice().toPlainString();

                System.out.printf(rowFmt + "%n",
                        coin.getId(),
                        coin.getName(),
                        coin.getSymbol(),
                        coin.getCurrency(),
                        priceStr,
                        coloredStatus);
            }
        }

        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);
        System.out.println(ConsoleColor.GRAY
                + "  Total: " + (coins != null ? coins.size() : 0) + " coin(s)"
                + ConsoleColor.RESET);
        System.out.println();
    }
}
