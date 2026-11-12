public class Hero {
    protected String name;
    protected String type;
    protected int hp;
    protected int maxHp;
    protected int damage;

    public Hero(String name, String type, int hp, int damage) {
        this.name = name;
        this.type = type;
        this.hp = hp;
        this.maxHp = hp;
        this.damage = damage;
    }

    public boolean isAlive() { return hp > 0; }

    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    public int getAttackDamage() { return damage; }

    public void attack(Boss boss, int multiplier) {
        boss.takeDamage(getAttackDamage() * multiplier);
    }

    // Пустая заглушка: у героев без способности ничего не происходит.
    public void useAbility(Hero[] team) { }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getHp() { return hp; }

    @Override
    public String toString() {
        if (!isAlive()) {
            return name + " (" + type + "): мёртв";
        }
        return name + " (" + type + "): " + hp + "/" + maxHp + " HP";
    }
    public void levelUp() {
        maxHp += 40;
        damage += 5;
        hp = maxHp;
    }
}