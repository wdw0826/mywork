public class Weapon {

    private String name;
    private int attackBonus;

    public Weapon(String name, int attackBonus) {
        this.name = name;
        this.attackBonus = attackBonus;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public String getName() {
        return name;
    }
}