public class Player extends Character {

    private int level = 1;
    private int exp = 0;
    private int money = 500;    // 初始金錢
    private int smallPotions = 0;
    private int bigPotions = 3;
    private int maxHp = 100;
    private Weapon weapon;

    public Player() {
        super(100, 10);
    }

    public int getTotalAttack() {
        return attack + (weapon != null ? weapon.getAttackBonus() : 0);
    }

    @Override
    public void attack(Character target) {
        int dmg = getTotalAttack();
        System.out.println("Player attacks for " + dmg);
        target.takeDamage(dmg);
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void gainExp(int amount) {
        exp += amount;
        if (exp >= 100) {
            level++;
            exp = 0;
            attack += 5;
            hp = maxHp;
            System.out.println("Level Up!");
        }
    }
    public void heal(int amount) {
        this.hp += amount;
        if (this.hp > maxHp) this.hp = maxHp;
    }

    // 金錢管理
    public int getMoney() { return money; }
    public void subMoney(int amount) { this.money -= amount; }
    public void addMoney(int amount) { this.money += amount; }

    // 藥水管理
    public int getSmallPotions() { return smallPotions; }
    public void addSmallPotions(int count) { this.smallPotions += count; }
    public void subSmallPotions(int count) { this.smallPotions -= count; }

    public int getBigPotions() { return bigPotions; }
    public void addBigPotions(int count) { this.bigPotions += count; }
    public void subBigPotions(int count) { this.bigPotions -= count; }


    public void setLevel(int level) { this.level = level; }
    public void setExp(int exp) { this.exp = exp; }
    public void setMoney(int money) { this.money = money; }
    public void setSmallPotions(int count) { this.smallPotions = count; }
    public void setBigPotions(int count) { this.bigPotions = count; }
    public void setHp(int hp) { this.hp = hp; }
    public Weapon getWeapon() { return this.weapon; }
    public int getLevel() { return this.level; }
    public int getExp() { return this.exp; }
    public int getMaxHp(){ return this.maxHp; }

    public void showStatus() {
        System.out.println("=== Player ===");
        System.out.println("Level: " + level);
        System.out.println("HP: " + hp + "/" + maxHp);
        System.out.println("Attack: " + getTotalAttack());
        System.out.println("Weapon: " +
                (weapon != null ? weapon.getName() : "None"));
        System.out.println("Big potions: " + bigPotions);
        System.out.println("Small potions: " + smallPotions);
    }
}