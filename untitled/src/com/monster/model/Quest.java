package com.monster.model;

public class Quest {

    private String name;
    private QuestRank rank;
    private QuestStatus status;
    private Monster monster;
    private boolean unlocked;

    public Quest(String name, QuestRank rank, Monster monster) {
        this.name = name;
        this.rank = rank;
        this.monster = monster;
        this.status = QuestStatus.AVAILABLE;
        this.unlocked = false;
    }

    public void start() {
        status = QuestStatus.IN_PROGRESS;
    }

    public void complete() {
        status = QuestStatus.COMPLETED;
    }

    public void unlock() {
        unlocked = true;
    }
    public void resetStatus() {
        this.status = QuestStatus.AVAILABLE;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
        // 如果你之前有做動態命名，這裡也要更新任務名稱
        this.name = "狩獵 " + monster.getName();
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public QuestRank getRank() { return rank; }
    public QuestStatus getStatus() { return status; }
    public Monster getMonster() { return monster; }
    public String getName() { return name; }
}