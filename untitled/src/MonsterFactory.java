public class MonsterFactory {

    public static Monster createMonster(QuestRank rank) {

        switch (rank) {
            case ONE_STAR:
                return new Monster("Slime", 50, 5);
            case TWO_STAR:
                return new Monster("Goblin", 80, 10);
            case THREE_STAR:
                return new Monster("Dragon", 120, 15);
            default:
                return null;
        }
    }
}