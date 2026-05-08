package com.monster.service;
import com.monster.model.*;
import com.monster.model.Quest;

import java.util.*;

public class QuestService {

    private List<Quest> quests = new ArrayList<>();

    public void addQuest(Quest q) {
        quests.add(q);
    }

    public void showQuests() {
        System.out.println("\n========== 獵人公會任務板 ==========");
        for (int i = 0; i < quests.size(); i++) {
            Quest q = quests.get(i);
            if (q.isUnlocked()) {
                Monster m = q.getMonster();
                System.out.printf("%d. %s (目標血量:%d) \n", i, q.getName(), m.getHp());
            }
        }
        System.out.println("====================================");

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