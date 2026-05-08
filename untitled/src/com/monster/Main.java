package com.monster;

import com.monster.model.*;
import com.monster.service.*;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. 初始化所有 Service 物件 (解決 qs 與 saveService 紅字)
        SaveService saveService = new SaveService();
        QuestService qs = new QuestService();
        StoreService storeService = new StoreService();

        saveService.initDatabase(); // 初始化資料庫表格
        qs.setupQuests();           // 初始化任務清單

        Scanner sc = new Scanner(System.in);
        Player player = null; // 解決 player 紅字

        System.out.println("=== 歡迎來到 Java 魔物獵人世界 ===");
        System.out.println("1. 讀取舊有獵人存檔");
        System.out.println("2. 建立新的獵人檔案");
        System.out.print("請選擇代碼: ");

        int startChoice = 0;
        if (sc.hasNextInt()) {
            startChoice = sc.nextInt();
            sc.nextLine();
        }

        if (startChoice == 1) {
            player = new Player("暫時名稱");
            // 呼叫 Service 的讀檔功能
            boolean isLoaded = saveService.load(player);

            if (!isLoaded || player.getName().equals("暫時名稱")) {
                System.out.println(">> [系統] 找不到現有存檔，改為建立新角色。");
                player = createNewPlayer(sc);
            } else {
                System.out.println(">> [系統] 歡迎回來，" + player.getName() + "！");
            }
        } else {
            player = createNewPlayer(sc);
        }

        // 遊戲主選單範例
        while (true) {
            System.out.println("\n【主選單】獵人：" + player.getName());
            System.out.println("1. 狀態 | 2. 任務板 | 3. 離開並存檔");
            System.out.print("指令: ");
            int cmd = sc.nextInt();

            if (cmd == 1) {
                player.showStatus();
            } else if (cmd == 2) {
                // 使用 qs 物件呼叫任務功能
                List<Quest> quests = qs.getAvailableQuests();
                for (int i = 0; i < quests.size(); i++) {
                    System.out.println(i + ". " + quests.get(i).getName());
                }
            } else if (cmd == 3) {
                saveService.save(player); // 離開前存檔
                break;
            }
        }
    }

    private static Player createNewPlayer(Scanner sc) {
        System.out.print("請輸入獵人的大名: ");
        String name = sc.nextLine();
        return new Player(name);
    }
}