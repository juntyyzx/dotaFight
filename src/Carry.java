public class Carry extends Hero {
    private int roundsPlayed = 0;

    public Carry(String name) {
        super(name, "Carry", 200, 10);
    }

    @Override
    public int getAttackDamage() {
        return damage + roundsPlayed * 5;
    }

    @Override
    public void useAbility(Hero[] team) {
        roundsPlayed++;
    }
}