import java.util.Scanner;

public class BattleService {
    public void startBattle(Player player, Quest quest) {
        Monster monster = quest.getMonster();
        System.out.println("\n========================================");
        System.out.println("   [ 任務開始 ] " + quest.getName());
        System.out.println("   目標確定：大型魔物 【" + monster.getName() + "】");
        System.out.println("========================================\n");

        Scanner sc = new Scanner(System.in);
        boolean usedPotionThisTurn = false; // 用來標記這回合是否喝水

        while (player.getHp() > 0 && monster.getHp() > 0) {
            System.out.println("\n玩家 HP: " + player.getHp() + " | 小藥水: " + player.getSmallPotions() + " | 大藥水: " + player.getBigPotions());
            System.out.println("行動：1.攻擊 2.喝小藥水(回20) 3.喝大藥水(回50)");

            int choice = sc.nextInt();
            int currentAttack = player.getTotalAttack();
            usedPotionThisTurn = false;

            if (choice == 2) {
                if (player.getSmallPotions() > 0) {
                    player.heal(20);
                    player.subSmallPotions(1);
                    usedPotionThisTurn = true;
                } else {
                    System.out.println("沒有小藥水！自動執行攻擊。");
                }
            } else if (choice == 3) {
                if (player.getBigPotions() > 0) {
                    player.heal(50);
                    player.subBigPotions(1);
                    usedPotionThisTurn = true;
                } else {
                    System.out.println("沒有大藥水！自動執行攻擊。");
                }
            }

            // 執行攻擊邏輯
            if (usedPotionThisTurn) {
                // 喝水後攻擊減半
                int debuffDmg = currentAttack / 2;
                System.out.println("喝水中... 攻擊力暫時減半！");
                monster.takeDamage(debuffDmg);
                System.out.println("玩家造成 " + debuffDmg + " 點傷害");
            } else {
                player.attack(monster);
            }

            // 怪物反擊
            if (monster.getHp() > 0) {
                monster.attack(player);
            }
        }

        if (player.getHp() > 0) {
            System.out.println("\n★ 狩獵成功！ ★");
            // 現在我們把它改回 AVAILABLE，讓它可以被重複接取
            quest.resetStatus();

            // 同時，我們讓這格任務的魔物「重生」成新的一隻
            quest.setMonster(MonsterFactory.createMonster(quest.getRank()));

            player.gainExp(50);
            player.addMoney(150);

            // 戰後回血：回復剩餘血量的 50%
            int lostHp = player.getMaxHp() - player.getHp();
            int recoverAmount = lostHp / 2;
            player.heal(recoverAmount);
            System.out.println("戰後休整：回復了剩餘血量的 50% (" + recoverAmount + " 點)。當前 HP: " + player.getHp());
        }
    }
}