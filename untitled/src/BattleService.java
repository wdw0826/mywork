public class BattleService {

    public void startBattle(Player player, Quest quest) {

        Monster monster = quest.getMonster();

        System.out.println("Start Quest: " + quest.getName());

        while (player.getHp() > 0 && monster.getHp() > 0) {

            player.attack(monster);

            if (monster.getHp() <= 0) break;

            monster.attack(player);
        }

        if (player.getHp() > 0) {
            System.out.println("Quest Cleared!");
            quest.complete();
            player.gainExp(50);
        } else {
            System.out.println("Failed...");
        }
    }
}