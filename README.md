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
