import java.util.*;

public class QuestService {

    private List<Quest> quests = new ArrayList<>();

    public void addQuest(Quest q) {
        quests.add(q);
    }

    public void showQuests() {
        System.out.println("=== Quest Board ===");

        for (int i = 0; i < quests.size(); i++) {
            Quest q = quests.get(i);

            if (!q.isUnlocked()) continue;

            System.out.println(i + ". " +
                    q.getRank() + " " +
                    q.getName() +
                    " [" + q.getStatus() + "]");
        }
    }

    public Quest acceptQuest(int idx) {
        Quest q = quests.get(idx);
        q.start();
        return q;
    }

    public void checkUnlocks() {

        boolean cleared = quests.stream()
                .anyMatch(q -> q.getRank() == QuestRank.ONE_STAR
                        && q.getStatus() == QuestStatus.COMPLETED);

        if (cleared) {
            quests.stream()
                    .filter(q -> q.getRank() == QuestRank.TWO_STAR)
                    .forEach(Quest::unlock);
        }
    }
}