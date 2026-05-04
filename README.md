# 🎮 Java 魔物獵人（Monster Hunter）模擬系統

這是一個基於 **Java 物件導向程式設計 (OOP)** 開發的文字冒險 RPG 系統。玩家扮演獵人，透過挑戰不同星級的魔物獲得經驗與金錢，並在商店中強化武器與補給，最終挑戰高難度的強大魔物。

---

## 🌟 系統特色

### 1. ⚔️ 策略性回合戰鬥
*   **動態決策**：玩家每回合可選擇「攻擊」或「喝水」。
*   **減傷機制**：選擇喝水回合雖無法攻擊，但可回復 HP 並獲得 **50% 傷害減免**。
*   **戰後休整**：成功狩獵後，系統會自動回復玩家剩餘血量的 50%，支援連續作戰。

### 2. ⚒️ 裝備與強化系統
*   **標籤強化**：商店強化武器後，名稱會動態標示 **(+1)**、**(+2)** 並提升攻擊力。
*   **經濟資源管理**：玩家需在強化武器與購買回復藥水之間取得金錢平衡。

### 3. 📜 多樣化任務板
*   **自由選擇**：全星級任務初始開放，玩家可自由選擇挑戰對象。
*   **動態重生**：任務完成後會自動重置，並由工廠產出新魔物，讓玩家可以持續刷怪。

### 4. 💾 存檔紀錄功能
*   **資料持久化**：支援 File IO，可保存等級、金錢、藥水數量及強化後的武器狀態。

---

## 🏗️ 系統架構圖 (UML Class Diagram)
```mermaid
classDiagram
    class Character {
        <<abstract>>
        -int hp
        -int maxHp
        -int attack
        +takeDamage(int dmg)
        +heal(int amount)
        +attack(Character target)*
    }

    class Player {
        -int level
        -int exp
        -int money
        -int smallPotions
        -int bigPotions
        -Weapon weapon
        +gainExp(int amount)
        +equipWeapon(Weapon w)
    }

    class Monster {
        -String name
        -int expValue
        +getName()
        +getExpValue()
    }

    class Weapon {
        -String name
        -int attackBonus
        +getName()
        +getAttackBonus()
    }

    class Quest {
        -String name
        -QuestRank rank
        -Monster monster
        -QuestStatus status
        +unlock()
        +resetStatus()
    }

    Character <|-- Player
    Character <|-- Monster
    Player "1" *-- "1" Weapon : 裝備
    Quest "1"
