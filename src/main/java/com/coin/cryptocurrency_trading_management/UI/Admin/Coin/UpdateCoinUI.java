package com.coin.cryptocurrency_trading_management.UI.Admin.Coin;


import com.coin.cryptocurrency_trading_management.Controller.CoinController;
import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinRequestUpdate;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class UpdateCoinUI {
    private final CoinController coinController;
    private final Scanner scanner;
    public void printUpdateForm(long id){
        System.out.println(ConsoleColor.CYAN + "==============================");
        System.out.println("         UPDATE COIN");
        System.out.println("==============================" + ConsoleColor.RESET);
        System.out.print("Coin Name: ");
        String name = scanner.nextLine().trim();
        System.out.println();
        System.out.print("Description: ");
        String description = scanner.nextLine().trim();
        var request = new CoinRequestUpdate();
        request.setId(id);
        request.setName(name);
        request.setDescription(description);
        try {
            coinController.updateCoin(request);
            System.out.println(ConsoleColor.GREEN + "\n✓ Coin update successfully!" + ConsoleColor.RESET);
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "\n✗ Failed to update coin: " + e.getMessage() + ConsoleColor.RESET);
        } catch (Exception e) {
            System.out.println(ConsoleColor.RED + "\n✗ Error: " + e.getMessage() + ConsoleColor.RESET);
        }
    }
}
