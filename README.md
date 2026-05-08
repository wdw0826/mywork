monster-hunter-project/
├── lib/               # 放置 postgresql-42.7.3.jar
├── sql/
│   └── schema.sql     # 資料庫建表與 5 筆初始資料
├── src/main/java/com/monster/
│   ├── Main.java      # 程式入口 (只負責啟動)
│   ├── config/        # DatabaseConfig.java (連線資訊)
│   ├── dao/           # PlayerDAO.java (純 SQL 操縱)
│   ├── model/         # Player.java, Weapon.java, enums/
│   ├── service/       # SaveService.java (業務邏輯)
│   └── view/          # GameView.java (負責印出選單、處理 Scanner)
├── README.md          # 內含 Mermaid 圖與截圖
└── .gitignore



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
*   **自由選擇**：全星級任務初始開放，每一星級皆提供 2-3 隻特色魔物供玩家挑選。
*   **動態重生**：任務完成後會自動重置狀態，並由工廠重新生成魔物，支援反覆刷怪。

### 4. 💾 存檔紀錄功能
*   **資料持久化**：支援 File IO，可完整保存等級、經驗、金錢、藥水數量及強化後的武器狀態。

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
    }

    class Quest {
        -String name
        -QuestRank rank
        -Monster monster
        +unlock()
        +resetStatus()
    }

    Character <|-- Player
    Character <|-- Monster
    Player "1" *-- "1" Weapon
    Quest "1" o-- "1" Monster
    
    class MonsterFactory {
        +createMonster(QuestRank rank, int type)$ Monster
    }

    class BattleService {
        +startBattle(Player p, Quest q)
    }

    class StoreService {
        +openStore(Player p)
    }

    class SaveService {
        +save(Player p)
        +load(Player p)
    }

    BattleService ..> Player
    BattleService ..> Monster
    StoreService ..> Player
    SaveService ..> Player
