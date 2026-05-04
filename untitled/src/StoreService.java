
import java.util.Scanner;

public class StoreService {
    public void openStore(Player player) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== 補給與鍛造屋 =====");
            System.out.println("當前持有金錢: " + player.getMoney());
            Weapon current = player.getWeapon();
            System.out.println("當前裝備武器: " + player.getWeapon().getName());
            System.out.println("------------------------");
            System.out.println("1. 購買 小回復藥 ($100)");
            System.out.println("2. 購買 大回復藥 ($200)");
            System.out.println("3. 強化當前武器 ($500) - 攻擊力 +15");
            System.out.println("4. 離開");

            int choice = sc.nextInt();
            if (choice == 1) {
                if (player.getMoney() >= 100) {
                    player.subMoney(100);
                    player.addSmallPotions(1);
                    System.out.println("購買成功！");
                } else {
                    System.out.println("金錢不足！");
                }
            } else if (choice == 2) {
                if (player.getMoney() >= 200) {
                    player.subMoney(200);
                    player.addBigPotions(1);
                    System.out.println("購買成功！");
                } else {
                    System.out.println("金錢不足！");
                }
            }
            if (choice == 3) {
                if (player.getMoney() >= 500) {
                    player.subMoney(500);

                    String newName;
                    int newAttack;

                    if (current == null) {
                        // 第一次強化，給予基礎武器
                        newName = "初階獵刀(+1)";
                        newAttack = 15;
                    } else {
                        String oldName = current.getName();
                        newAttack = current.getAttackBonus() + 15;

                        // 邏輯：檢查名稱中是否含有 "(+"
                        if (oldName.contains("(+")) {
                            // 拆解字串找出數字。例如 "獵刀(+1)" -> 提取出 1
                            int startIndex = oldName.lastIndexOf("(+") + 2;
                            int endIndex = oldName.lastIndexOf(")");
                            try {
                                int level = Integer.parseInt(oldName.substring(startIndex, endIndex));
                                level++; // 等級加 1
                                // 重新組合名字，保留原始名稱但更新數字
                                String baseName = oldName.substring(0, oldName.lastIndexOf("(+"));
                                newName = baseName + "(+" + level + ")";
                            } catch (Exception e) {
                                newName = oldName + "(+1)"; // 萬一解析失敗的防呆
                            }
                        } else {
                            // 如果原本沒有標號，直接加 (+1)
                            newName = oldName + "(+1)";
                        }
                    }

                    player.equipWeapon(new Weapon(newName, newAttack));
                    System.out.println(">> 武器強化為 " + newName + "，攻擊力提升至 " + newAttack + "！");
                } else {
                    System.out.println(">> 金錢不足，無法強化。");
                }
            } else if (choice == 4) {
                break;
            }
        }
    }
}