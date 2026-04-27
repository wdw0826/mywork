public class MonsterFactory {
    public static Monster createMonster(QuestRank rank, int type) {
        switch (rank) {
            case ONE_STAR:
                if (type == 0) return new Monster("青熊獸", 60, 5, 30);
                if (type == 1) return new Monster("藍速龍", 40, 8, 25);
                return new Monster("草食龍", 30, 2, 15);
            case TWO_STAR:
                if (type == 0) return new Monster("雌火龍", 150, 18, 120);
                if (type == 1) return new Monster("奇猿狐", 120, 22, 110);
                return new Monster("岩龍", 200, 12, 130);
            case THREE_STAR:
                if (type == 0) return new Monster("轟龍", 400, 50, 350);
                if (type == 1) return new Monster("雄火龍", 350, 60, 400);
                return new Monster("碎龍", 450, 55, 450);
            default:
                return new Monster("小豬", 10, 1, 5);
        }
    }
}