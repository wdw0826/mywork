import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Player player = new Player();
        player.equipWeapon(new Weapon("Iron Sword", 5));

        QuestService qs = new QuestService();
        BattleService bs = new BattleService();

        // 建任務
        Monster m1 = MonsterFactory.createMonster(QuestRank.ONE_STAR);
        // 命名任務，例如「狩獵 藍速龍」
        Quest q1 = new Quest("狩獵 " + m1.getName(), QuestRank.ONE_STAR, m1);
        q1.unlock();

        Monster m2 = MonsterFactory.createMonster(QuestRank.TWO_STAR);
        Quest q2 = new Quest("狩獵 " + m2.getName(), QuestRank.TWO_STAR, m2);

        qs.addQuest(q1);
        qs.addQuest(q2);

        SaveService saveService = new SaveService(); // 存檔用

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("1. 狀態 | 2. 任務板 | 3. 接任務 | 4. 商店 | 5. 存檔 | 6. 讀取存檔 | 7.離開");
            int c = sc.nextInt();

            switch (c) {
                case 1 -> player.showStatus();
                case 2 -> qs.showQuests();
                case 3 -> {
                    qs.showQuests();
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