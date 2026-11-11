import java.util.Random;

public class Boss {
    private int hp;
    private int damage;
    private String defenceType = "";
    private Random random = new Random();

    public Boss(int hp, int damage) {
        this.hp = hp;
        this.damage = damage;
    }

    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void chooseDefence(String[] types) {
        defenceType = types[random.nextInt(types.length)];
    }

    public int getHp() { return hp; }
    public int getDamage() { return damage; }
    public String getDefenceType() { return defenceType; }
}