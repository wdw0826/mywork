import java.util.Scanner;

public class BattleService {
    public void startBattle(Player player, Quest quest) {
        Monster monster = quest.getMonster();
        Scanner sc = new Scanner(System.in);
        System.out.println("\n===== 遭遇魔物：" + monster.getName() + " =====");

        while (player.getHp() > 0 && monster.getHp() > 0) {
            System.out.println("\n" + monster.getName() + " HP: " + monster.getHp());
            System.out.println("玩家 HP: " + player.getHp() + " | 藥水剩餘: " + player.getPotions());
            System.out.println("請選擇行動：1. 攻擊  2. 喝水 (回20)  3. 強力喝水 (回50)");

            int choice = sc.nextInt();

            // --- 玩家回合 ---
            if (choice == 1) {
                player.attack(monster);
            } else if (choice == 2 || choice == 3) {
                if (player.getPotions() > 0) {
                    int healAmount = (choice == 2) ? 20 : 50;
                    player.heal(healAmount);
                } else {
                    System.out.println("沒藥水了！強行轉為攻擊！");
                    player.attack(monster);
                }
            }

            // --- 怪物回合 (如果怪物還活著) ---
            if (monster.getHp() > 0) {
                System.out.println(monster.getName() + " 發動攻擊！");
                monster.attack(player);
                System.out.println("玩家受到傷害，剩餘 HP: " + player.getHp());
            }
        }

        // --- 結算 ---
        if (player.getHp() > 0) {
            System.out.println("\n★ 任務完成！順利狩獵 " + monster.getName() + " ★");
            quest.complete();
            player.gainExp(50);
        } else {
            System.out.println("\n[力盡倒下] 你被 " + monster.getName() + " 擊敗了...");
        }
    }
}