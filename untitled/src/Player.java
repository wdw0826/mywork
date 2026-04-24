public class Player extends Character {

    private int level = 1;
    private int exp = 0;
    private int maxHp = 100;
    private Weapon weapon;
    private int potions = 3;

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
            maxHp += 20;
            hp = maxHp;
            System.out.println("Level Up!");
        }
    }
    public void heal(int amount) {
        this.hp += amount;
        if (this.hp > maxHp) this.hp = maxHp; // 不超過血量上限
        System.out.println("使用藥水！回復了 " + amount + " 點 HP。當前 HP: " + this.hp);
        this.potions--;
    }

    public int getPotions() { return potions; }
    public void addPotions(int count) { this.potions += count; }

    public void showStatus() {
        System.out.println("=== Player ===");
        System.out.println("Level: " + level);
        System.out.println("HP: " + hp + "/" + maxHp);
        System.out.println("Attack: " + getTotalAttack());
        System.out.println("Weapon: " +
                (weapon != null ? weapon.getName() : "None"));
    }
}