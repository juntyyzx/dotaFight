import java.util.Random;

public class Game {
    private Hero[] heroes;
    private Boss boss;
    private int round = 0;
    private Random random = new Random();
    private String[] types = {"Carry", "Mid", "Offlaner", "Support"};

    public Game() {
        boss = new Boss(700, 50);
        heroes = new Hero[]{
                new Carry("Anti-Mage"),
                new Mid("Invoker"),
                new Offlaner("Axe"),
                new Support("Crystal Maiden")
        };
    }

    public boolean isFinished() {
        if (!boss.isAlive()) {
            return true;
        }
        for (Hero h : heroes) {
            if (h.isAlive()) {
                return false;
            }
        }
        return true;
    }

    private Hero chooseLucky() {
        Hero lucky;
        do {
            lucky = heroes[random.nextInt(heroes.length)];
        } while (!lucky.isAlive());
        return lucky;
    }

    private void heroesHit(Hero lucky) {
        for (Hero h : heroes) {
            if (!h.isAlive()) {
                continue;
            }
            if (h.getType().equals(boss.getDefenceType())) {
                System.out.println(h.getName() + " заблокирован защитой босса");
                continue;
            }
            int multiplier = 1;
            if (h == lucky) {
                multiplier = random.nextInt(9) + 2;
                System.out.println("КРИТ! " + h.getName() + " бьёт с множителем x" + multiplier);
            }
            h.attack(boss, multiplier);
            if (!boss.isAlive()) {
                break;
            }
        }
    }

    private void bossHits() {
        for (Hero h : heroes) {
            if (h.isAlive()) {
                h.takeDamage(boss.getDamage());
            }
        }
    }

    private void useAbilities() {
        for (Hero h : heroes) {
            if (h.isAlive()) {
                h.useAbility(heroes);
            }
        }
    }

    public void playRound() {
        round++;
        System.out.println("\n===== Раунд " + round + " =====");
        boss.chooseDefence(types);
        System.out.println("Босс защищается от: " + boss.getDefenceType());
        Hero lucky = chooseLucky();
        System.out.println("Счастливчик: " + lucky.getName());

        heroesHit(lucky);
        if (boss.isAlive()) {
            bossHits();
            useAbilities();
        }
        printStatistics();
    }

    private void printStatistics() {
        System.out.println("-------------");
        System.out.println("Здоровье босса: " + boss.getHp());
        for (Hero h : heroes) {
            System.out.println(h);
        }
        System.out.println("-------------");
    }

    public void printResult() {
        if (boss.isAlive()) {
            System.out.println("Босс победил!");
        } else {
            System.out.println("Герои победили!");
        }
    }
}