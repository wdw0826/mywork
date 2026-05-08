package com.monster.service;

import com.monster.model.*;
import java.util.*;

/**
 * 任務邏輯層：負責管理任務板、初始化任務以及解鎖邏輯
 */
public class QuestService {

    private List<Quest> quests = new ArrayList<>();

    // 【新增】初始化任務的方法，Main.java 中呼叫 qs.setupQuests() 需要它
    public void setupQuests() {
        quests.clear();

        // 取得所有難度等級
        for (QuestRank rank : QuestRank.values()) {
            // 每個難度等級生成 3 個任務
            for (int i = 0; i < 3; i++) {
                // 使用 MonsterFactory 建立魔物，解決 MonsterFactory 紅字
                Monster m = MonsterFactory.createMonster(rank, i);

                // 建立任務物件 (名稱, 等級, 魔物)
                Quest q = new Quest("[" + rank.getLabel() + "] 狩獵 " + m.getName(), rank, m);

                // 預設解鎖一星任務，其餘鎖定
                if (rank == QuestRank.ONE_STAR) {
                    q.unlock();
                }

                this.addQuest(q);
            }
        }
    }

    public void addQuest(Quest q) {
        quests.add(q);
    }

    // 回傳清單，供 Main.java 中的 List<Quest> quests = qs.getAvailableQuests() 使用
    public List<Quest> getAvailableQuests() {
        return quests;
    }

    public void showQuests() {
        System.out.println("\n========== 獵人公會任務板 ==========");
        for (int i = 0; i < quests.size(); i++) {
            Quest q = quests.get(i);
            if (q.isUnlocked()) {
                Monster m = q.getMonster();
                // 這裡要確認你的 Monster.java 有 getHp() 方法
                System.out.printf("%d. %s (目標血量:%d) \n", i, q.getName(), m.getHp());
            }
        }
        System.out.println("====================================");
    }

    public Quest acceptQuest(int idx) {
        if (idx < 0 || idx >= quests.size()) {
            System.out.println(">> [錯誤] 無效的任務編號。");
            return null;
        }
        Quest q = quests.get(idx);
        q.start();
        return q;
    }

    /**
     * 檢查是否達成解鎖下一等級任務的條件
     */
    public void checkUnlocks() {
        // 檢查是否有任一星任務已完成
        boolean clearedOneStar = quests.stream()
                .anyMatch(q -> q.getRank() == QuestRank.ONE_STAR
                        && q.getStatus() == QuestStatus.COMPLETED);

        // 如果一星完成，解鎖二星
        if (clearedOneStar) {
            quests.stream()
                    .filter(q -> q.getRank() == QuestRank.TWO_STAR)
                    .forEach(Quest::unlock);
        }

        // 檢查是否有二星任務已完成
        boolean clearedTwoStar = quests.stream()
                .anyMatch(q -> q.getRank() == QuestRank.TWO_STAR
                        && q.getStatus() == QuestStatus.COMPLETED);

        // 如果二星完成，解鎖三星
        if (clearedTwoStar) {
            quests.stream()
                    .filter(q -> q.getRank() == QuestRank.THREE_STAR)
                    .forEach(Quest::unlock);
        }
    }
}