public class Support extends Hero {
    private int healAmount = 40;

    public Support(String name) {
        super(name, "Support", 220, 5);
    }

    @Override
    public void useAbility(Hero[] team) {
        if (!isAlive()) {
            return;
        }
        Hero target = null;
        for (Hero h : team) {
            if (!h.isAlive()) {
                continue;
            }
            if (target == null || h.getHp() < target.getHp()) {
                target = h;
            }
        }
        if (target != null) {
            target.heal(healAmount);
            System.out.println(name + " лечит " + target.getName() + " на " + healAmount);
        }
    }
}