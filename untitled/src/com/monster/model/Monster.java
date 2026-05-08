package com.monster.model;

public class Monster extends Character {
    private String name;
    private int expValue; // 新增經驗值欄位

    // 必須有這個 4 個參數的建構子
    public Monster(String name, int hp, int attack, int expValue) {
        super(hp, attack); // 傳給父類別 Character
        this.name = name;
        this.expValue = expValue;
    }

    public String getName() { return name; }
    public int getattack() { return attack; }
}