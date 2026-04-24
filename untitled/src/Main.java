import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Player player = new Player();
        player.equipWeapon(new Weapon("Iron Sword", 5));

        QuestService qs = new QuestService();
        BattleService bs = new BattleService();

        // 建任務
        Quest q1 = new Quest("Slime Hunt",
                QuestRank.ONE_STAR,
                MonsterFactory.createMonster(QuestRank.ONE_STAR));

        q1.unlock();

        Quest q2 = new Quest("Goblin Hunt",
                QuestRank.TWO_STAR,
                MonsterFactory.createMonster(QuestRank.TWO_STAR));

        qs.addQuest(q1);
        qs.addQuest(q2);

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n1. Status");
            System.out.println("2. Quest Board");
            System.out.println("3. Start Quest");
            System.out.println("4. Exit");

            int c = sc.nextInt();

            if (c == 1) player.showStatus();
            else if (c == 2) qs.showQuests();
            else if (c == 3) {
                qs.showQuests();
                int i = sc.nextInt();

                Quest q = qs.acceptQuest(i);
                bs.startBattle(player, q);

                qs.checkUnlocks();
            }
            else break;
        }
    }
}