import java.util.Scanner;

public class BattleService {
    public void startBattle(Player player, Quest quest) {
        Monster monster = quest.getMonster();
        Scanner sc = new Scanner(System.in);
        System.out.println("\n===== 遭遇魔物：" + monster.getName() + " =====");

        while (player.getHp() > 0 && monster.getHp() > 0) {
            System.out.println("\n" + monster.getName() + " HP: " + monster.getHp());
            System.out.println("玩家 HP: " + player.getHp() + " | 小藥水: " + player.getSmallPotions() + " | 大藥水: " + player.getBigPotions());
            System.out.println("行動：1. 攻擊  2. 喝小藥水(回20+減傷50%)  3. 喝大藥水(回50+減傷50%) 4.退出");

            int choice = sc.nextInt();
            boolean isDefending = false; // 紀錄本回合是否處於喝水減傷狀態

            // --- 玩家回合 ---
            if (choice == 1) {
                player.attack(monster); // 正常攻擊
            } else if (choice == 2 || choice == 3) {
                // 檢查藥水數量
                if ((choice == 2 && player.getSmallPotions() > 0) || (choice == 3 && player.getBigPotions() > 0)) {
                    int healAmount = (choice == 2) ? 20 : 50;
                    player.heal(healAmount);
                    if (choice == 2) player.subSmallPotions(1);
                    else player.subBigPotions(1);

                    isDefending = true; // 開啟減傷標記
                    System.out.println(">> 喝水中！本回合無法攻擊，但獲得 50% 減傷效果！");
                } else {
                    System.out.println(">> 沒藥水了！強制改為攻擊！");
                    player.attack(monster);
                }
            }else if(choice == 4){
                System.out.println("離開戰鬥，返回主畫面");
                return;
            }

            // --- 魔物回合 ---
            if (monster.getHp() > 0) {
                System.out.println(monster.getName() + " 發動攻擊！");

                int originalDmg = monster.getattack();
                int finalDmg = originalDmg;

                if (isDefending) {
                    finalDmg = originalDmg / 2; // 傷害減半
                    System.out.println(">> [防禦生效] 玩家採取喝水姿勢，抵擋了 50% 的傷害！");
                }

                player.takeDamage(finalDmg);
                System.out.println("玩家受到 " + finalDmg + " 點傷害，剩餘 HP: " + player.getHp());
            }
        }
        //---狩獵結果---
        if (player.getHp() > 0 && monster.getHp() <= 0) {
            System.out.println("\n★ 狩獵成功！ ★");
            quest.resetStatus();

            //重生新的一隻
            quest.setMonster(MonsterFactory.createMonster(quest.getRank(),0));

            player.gainExp(50);
            player.addMoney(150);

            // 戰後回血：回復剩餘血量的 50%
            int lostHp = player.getMaxHp() - player.getHp();
            int recoverAmount = lostHp / 2;
            player.heal(recoverAmount);
            System.out.println("戰後休整：回復了剩餘血量的 50% (" + recoverAmount + " 點)。當前 HP: " + player.getHp());
        }else{
            System.out.println("狩獵失敗!即將退回主畫面，並將血量回復至30");
            System.out.println("====================================");
            player.setHp(30);
        }
    }
}