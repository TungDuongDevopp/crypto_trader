package com.coin.cryptocurrency_trading_management.UI.Admin;

import com.coin.cryptocurrency_trading_management.Controller.SessionController;
import com.coin.cryptocurrency_trading_management.DTO.Session.SessionResponse;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class AdminSessionUI {
    private final SessionController sessionController;
    private final Scanner scanner;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void show() {
        while (true) {
            printTableSession();
            System.out.println(ConsoleColor.BOLD + "  Options:" + ConsoleColor.RESET);
            System.out.println("    0. Back to Dashboard");
            System.out.print("  Choose: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "0" -> { return; }
                default  -> System.out.println(ConsoleColor.YELLOW + "  Invalid choice, please try again." + ConsoleColor.RESET);
            }
        }
    }

    private void printTableSession() {
        List<SessionResponse> sessions;
        try {
            sessions = sessionController.getAllSessions();
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "  Can't get session list. Please try again." + ConsoleColor.RESET);
            return;
        }

        // Cột: ID (4) | USER ID (7) | LOGIN TIME (19) | EXPIRE TIME (19) | STATUS (10)
        String separator = "+------+---------+---------------------+---------------------+------------+";
        String headerFmt = "| %-4s | %-7s | %-19s | %-19s | %-10s |";
        String rowFmt    = "| %-4d | %-7d | %-19s | %-19s | %s |";

        System.out.println();
        System.out.println(ConsoleColor.CYAN + ConsoleColor.BOLD
                + "                            SESSION MANAGEMENT" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);
        System.out.printf(ConsoleColor.BOLD + headerFmt + ConsoleColor.RESET + "%n",
                "ID", "USER ID", "LOGIN TIME", "EXPIRE TIME", "STATUS");
        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);

        if (sessions == null || sessions.isEmpty()) {
            System.out.println(ConsoleColor.YELLOW + "  No sessions found." + ConsoleColor.RESET);
        } else {
            for (SessionResponse session : sessions) {
                String statusText = session.isActive() ? "ACTIVE" : "EXPIRED";
                String statusColor = session.isActive() ? ConsoleColor.GREEN : ConsoleColor.RED;

                // Căn lề chuỗi thuần 10 ký tự trước rồi bọc mã màu để không lệch bảng
                String formattedStatus = String.format("%-10s", statusText);
                String coloredStatus = statusColor + formattedStatus + ConsoleColor.RESET;

                String loginTimeStr = session.getLoginTime() != null
                        ? session.getLoginTime().format(DATE_TIME_FORMATTER)
                        : "N/A";
                String expireTimeStr = session.getExpireTime() != null
                        ? session.getExpireTime().format(DATE_TIME_FORMATTER)
                        : "N/A";

                System.out.printf(rowFmt + "%n",
                        session.getId(),
                        session.getUserId(),
                        loginTimeStr,
                        expireTimeStr,
                        coloredStatus);
            }
        }

        System.out.println(ConsoleColor.CYAN + separator + ConsoleColor.RESET);
        System.out.println(ConsoleColor.GRAY
                + "  Total: " + (sessions != null ? sessions.size() : 0) + " session(s)"
                + ConsoleColor.RESET);
        System.out.println();
    }
}
