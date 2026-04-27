
import java.util.Scanner;

public class StoreService {
    public void openStore(Player player) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== 補給商店 =====");
            System.out.println("當前金錢: " + player.getMoney());
            System.out.println("1. 買小回復藥 ($100) - 當前持有: " + player.getSmallPotions());
            System.out.println("2. 買大回復藥 ($200) - 當前持有: " + player.getBigPotions());
            System.out.println("3. 離開商店");

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
            } else {
                break;
            }
        }
    }
}