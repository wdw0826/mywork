public class Monster extends Character {

    private String name;

    public Monster(String name, int hp, int attack) {
        super(hp, attack);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}