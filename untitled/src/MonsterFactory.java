public class MonsterFactory {
    public static Monster createMonster(QuestRank rank) {
        switch (rank) {
            case ONE_STAR:
                // 1星怪增加：藍速龍、青熊獸
                return Math.random() > 0.5
                        ? new Monster("藍速龍", 40, 6)
                        : new Monster("青熊獸", 60, 4);
            case TWO_STAR:
                // 2星怪增加：雌火龍、奇猿狐
                return Math.random() > 0.5
                        ? new Monster("雌火龍", 100, 12)
                        : new Monster("奇猿狐", 85, 15);
            case THREE_STAR:
                // 3星怪增加：雄火龍、轟龍
                return Math.random() > 0.5
                        ? new Monster("雄火龍", 200, 25)
                        : new Monster("轟龍", 180, 35);
            default:
                return new Monster("小豬", 10, 1);
        }
    }
}