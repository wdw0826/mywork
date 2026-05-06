import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SaveService saveService = new SaveService();
        QuestService qs = new QuestService();
        BattleService bs = new BattleService();
        Player player = null;

        saveService.initDatabase();

        // --- 初始化任務池 (維持你原本的邏輯) ---
        setupQuests(qs);

        // --- 遊戲啟動入口：詢問讀檔或新檔案 ---
        System.out.println("=== 歡迎來到 Java 魔物獵人世界 ===");
        System.out.println("1. 讀取舊有獵人存檔 (資料庫)");
        System.out.println("2. 建立新的獵人檔案");
        System.out.print("請選擇代碼: ");

        int startChoice = 0;
        if (sc.hasNextInt()) {
            startChoice = sc.nextInt();
            sc.nextLine(); // 消耗換行符
        }

        if (startChoice == 1) {
            // 讀檔模式
            player = new Player("暫時名稱");
            saveService.load(player);
            // 檢查是否真的有讀到資料 (如果名字沒變代表資料庫沒東西)
            if (player.getName().equals("暫時名稱")) {
                System.out.println(">> [系統] 找不到現有存檔，改為建立新角色。");
                player = createNewPlayer(sc);
            } else {
                System.out.println(">> [系統] 歡迎回來，" + player.getName() + "！");
            }
        } else {
            // 新檔案模式
            player = createNewPlayer(sc);
        }

        // --- 進入主遊戲迴圈 ---
        while (true) {
            System.out.println("\n--------------------------------------------------");
            System.out.println("【主選單】獵人: " + player.getName());
            System.out.println("1. 狀態 | 2. 任務板 | 3. 接任務 | 4. 商店 | 5. 存檔並離開 | 6. 讀取存檔 | 7. 直接離開");
            System.out.print("指令: ");

            if (!sc.hasNextInt()) {
                sc.next();
                continue;
            }
            int c = sc.nextInt();

            switch (c) {
                case 1 -> player.showStatus();
                case 2 -> qs.showQuests();
                case 3 -> {
                    qs.showQuests();
                    System.out.print("請輸入欲挑戰的任務編號: ");
                    int i = sc.nextInt();
                    Quest q = qs.acceptQuest(i);
                    if (q != null) {
                        bs.startBattle(player, q);
                        qs.checkUnlocks();
                    }
                }
                case 4 -> new StoreService().openStore(player);
                case 5 -> {
                    saveService.save(player);
                    System.out.println(">> [系統] 遊戲進度已安全存入資料庫。再見！");
                    return;
                }
                case 6 -> {
                    saveService.load(player);
                    System.out.println(">> [系統] 已重新載入存檔。");
                    player.showStatus();
                }
                case 7 -> {
                    System.out.println(">> [系統] 結束遊戲（未存檔）。");
                    return;
                }
                default -> System.out.println(">> [錯誤] 無效指令。");
            }
        }
    }

    // 建立新玩家的私有方法
    private static Player createNewPlayer(Scanner sc) {
        System.out.print("請輸入獵人的大名: ");
        String name = sc.nextLine();
        Player p = new Player(name);
        p.equipWeapon(new Weapon("初階獵刀", 5));
        System.out.println(">> [系統] 獵人 " + name + " 準備就緒！");
        return p;
    }

    // 初始化任務池
    private static void setupQuests(QuestService qs) {
        for (QuestRank rank : QuestRank.values()) {
            for (int i = 0; i < 3; i++) {
                Monster m = MonsterFactory.createMonster(rank, i);
                if (m != null) {
                    Quest q = new Quest("【" + rank.getLabel() + "】" + m.getName() + "狩獵", rank, m);
                    q.unlock();
                    qs.addQuest(q);
                }
            }
        }
    }
}