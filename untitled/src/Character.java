
public abstract class Character {

    protected int hp;
    protected int attack;

    public Character(int hp, int attack) {
        this.hp = hp;
        this.attack = attack;
    }

    public void attack(Character target) {
        target.takeDamage(attack);
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
    }

    public int getHp() {
        return hp;
    }
}