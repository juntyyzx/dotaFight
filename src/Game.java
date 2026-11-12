
import java.util.Random;
import java.util.Scanner;

public class Game {
    private static final int MAX_LEVEL = 3;

    private Hero[] heroes;
    private Boss boss;
    private int level = 1;
    private int round = 0;
    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);
    private String[] types = {"Carry", "Mid", "Offlaner", "Support"};

    public Game() {
        heroes = new Hero[]{
                new Carry("Anti-Mage"),
                new Mid("Invoker"),
                new Offlaner("Axe"),
                new Support("Crystal Maiden")
        };
    }

    // ---------- Главный цикл по уровням ----------

    public void run() {
        for (level = 1; level <= MAX_LEVEL; level++) {
            startLevel();
            while (!isFinished()) {
                playRound();
            }
            if (allHeroesDead()) {
                break;
            }
            if (level < MAX_LEVEL) {
                System.out.println("\n*** Уровень " + level + " пройден! Герои становятся сильнее. ***");
                for (Hero h : heroes) {
                    h.levelUp();
                }
            }
        }
        printResult();
    }

    private void startLevel() {
        round = 0;
        // Чем выше уровень, тем сильнее босс.
        boss = new Boss(700 + (level - 1) * 250, 50 + (level - 1) * 10);
        System.out.println("\n########## УРОВЕНЬ " + level + " из " + MAX_LEVEL + " ##########");
        System.out.println("Босс: " + boss.getHp() + " HP, урон " + boss.getDamage());
        printStatistics();
    }

    // ---------- Состояние игры ----------

    private boolean allHeroesDead() {
        for (Hero h : heroes) {
            if (h.isAlive()) {
                return false;
            }
        }
        return true;
    }

    public boolean isFinished() {
        return !boss.isAlive() || allHeroesDead();
    }

    // ---------- Ввод игрока ----------

    // Читает число от min до max, пока игрок не введёт корректное.
    private int readChoice(int min, int max) {
        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                System.out.println("\nВвод закончился, игра завершена.");
                System.exit(0);
            }
            String line = scanner.nextLine().trim();
            for (int i = min; i <= max; i++) {
                if (line.equals(String.valueOf(i))) {
                    return i;
                }
            }
            System.out.println("Введите число от " + min + " до " + max + ".");
        }
    }

    private int chooseAction() {
        System.out.println("Ваше действие:");
        System.out.println("  1 - Атака (все бьют, один случайный герой делает крит)");
        System.out.println("  2 - Оборона (никто не бьёт, урон босса вдвое меньше, герои отдыхают +20 HP)");
        System.out.println("  3 - Усилить героя (он бьёт x4, но получает от босса вдвое больше)");
        return readChoice(1, 3);
    }

    private Hero chooseHero() {
        System.out.println("Кого усилить?");
        for (int i = 0; i < heroes.length; i++) {
            System.out.println("  " + (i + 1) + " - " + heroes[i]);
        }
        while (true) {
            Hero chosen = heroes[readChoice(1, heroes.length) - 1];
            if (chosen.isAlive()) {
                return chosen;
            }
            System.out.println("Этот герой мёртв, выберите другого.");
        }
    }

    // ---------- Раунд ----------

    private Hero chooseLucky() {
        Hero lucky;
        do {
            lucky = heroes[random.nextInt(heroes.length)];
        } while (!lucky.isAlive());
        return lucky;
    }

    // special бьёт с множителем specialMultiplier, остальные с множителем 1.
    private void heroesHit(Hero special, int specialMultiplier) {
        for (Hero h : heroes) {
            if (!h.isAlive()) {
                continue;
            }
            if (h.getType().equals(boss.getDefenceType())) {
                System.out.println(h.getName() + " заблокирован защитой босса");
                continue;
            }
            int multiplier = 1;
            if (h == special) {
                multiplier = specialMultiplier;
                System.out.println("КРИТ! " + h.getName() + " бьёт с множителем x" + multiplier);
            }
            h.attack(boss, multiplier);
            if (!boss.isAlive()) {
                break;
            }
        }
    }

    // defending: урон босса вдвое меньше. focused: получает двойной урон.
    private void bossHits(boolean defending, Hero focused) {
        int damage = boss.getDamage();
        if (defending) {
            damage = damage / 2;
        }
        for (Hero h : heroes) {
            if (!h.isAlive()) {
                continue;
            }
            if (h == focused) {
                h.takeDamage(damage * 2);
            } else {
                h.takeDamage(damage);
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

    private void playRound() {
        round++;
        System.out.println("\n===== Уровень " + level + ", раунд " + round + " =====");
        boss.chooseDefence(types);
        System.out.println("Босс защищается от: " + boss.getDefenceType());

        int action = chooseAction();
        boolean defending = false;
        Hero focused = null;

        if (action == 1) {
            Hero lucky = chooseLucky();
            System.out.println("Счастливчик: " + lucky.getName());
            heroesHit(lucky, random.nextInt(9) + 2);
        } else if (action == 2) {
            defending = true;
            System.out.println("Герои занимают оборону.");
        } else {
            focused = chooseHero();
            heroesHit(focused, 4);
        }

        if (boss.isAlive()) {
            bossHits(defending, focused);
            useAbilities();
            if (defending) {
                for (Hero h : heroes) {
                    if (h.isAlive()) {
                        h.heal(20);
                    }
                }
            }
        }
        printStatistics();
    }

    // ---------- Вывод ----------

    private void printStatistics() {
        System.out.println("-------------");
        System.out.println("Здоровье босса: " + boss.getHp());
        for (Hero h : heroes) {
            System.out.println(h);
        }
        System.out.println("-------------");
    }

    private void printResult() {
        if (allHeroesDead()) {
            System.out.println("\nБосс победил! Вы дошли до уровня " + level + " из " + MAX_LEVEL + ".");
        } else {
            System.out.println("\nПОБЕДА! Все " + MAX_LEVEL + " уровня пройдены!");
        }
    }
}
