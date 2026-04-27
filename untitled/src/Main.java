import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Player player = new Player();
        player.equipWeapon(new Weapon("初階獵刀", 5));

        QuestService qs = new QuestService();

// 一星任務池
        for (int i = 0; i < 3; i++) {
            Monster m = MonsterFactory.createMonster(QuestRank.ONE_STAR, i);
            Quest q = new Quest("【1★】" + m.getName() + "狩獵", QuestRank.ONE_STAR, m);
            q.unlock(); // 直接全部開放
            qs.addQuest(q);
        }

// 二星任務池
        for (int i = 0; i < 3; i++) {
            Monster m = MonsterFactory.createMonster(QuestRank.TWO_STAR, i);
            Quest q = new Quest("【2★】" + m.getName() + "狩獵", QuestRank.TWO_STAR, m);
            q.unlock();
            qs.addQuest(q);
        }

// 三星任務池
        for (int i = 0; i < 3; i++) {
            Monster m = MonsterFactory.createMonster(QuestRank.THREE_STAR, i);
            Quest q = new Quest("【3★】" + m.getName() + "狩獵", QuestRank.THREE_STAR, m);
            q.unlock();
            qs.addQuest(q);
        }
        BattleService bs = new BattleService();

        SaveService saveService = new SaveService(); // 存檔用

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("1. 狀態 | 2. 任務板 | 3. 接任務 | 4. 商店 | 5. 離開並存檔 | 6. 讀取存檔 | 7.離開(不存檔)" );
            int c = sc.nextInt();

            switch (c) {
                case 1 -> player.showStatus();
                case 2 -> qs.showQuests();
                case 3 -> {
                    qs.showQuests();
                    System.out.println("請輸入欲挑戰的任務編號: ");
                    int i = sc.nextInt();
                    Quest q = qs.acceptQuest(i);
                    bs.startBattle(player, q);
                    qs.checkUnlocks();
                }
                case 4 -> new StoreService().openStore(player);
                case 5 -> {
                    saveService.save(player);
                    System.out.println("已存檔");
                    return;
                }
                case 6 -> {
                    saveService.load(player); // 呼叫讀檔方法
                    player.showStatus();
                }
                case 7 ->{
                    System.out.println("再見");
                    return;
                }
                default -> System.out.println("錯誤");
            }
        }
    }
}