<h1>Рпг игра 2.0 с выбором действий и уровнями</h1>
За основу взята механика из игры Дота 2: 
Carry - Герой, становящийся сильнее с каждым раундом
Mid - Баланс урона и HP
Offlaner - Некий танк, больше HP, меньше урона
Support - Лечит героя с наименьшим кол-вом хп на 40  
4 героя. Босс. 3 уровня. Задача: пройти их всех. Сможете ли вы пройти их всех подряд?
Игра работает через классы: Boss.java - самостоятельный класс, Carry, Mid, Offlaner, Support - Наследуют от Hero.java.
Game - класс, собирающий всю игру воедино.

import java.util.Random; -Импорт утилиты в джаве "Рандом" для того чтобы потом случайным образом выбирать индекс "счастливчика" и коэффицент урона


public class Boss {
    private int hp;
    private int damage;
    private String defenceType = "";
    private Random random = new Random(); "Создаем приватные переменные чтобы они могли использоваться только здесь "

    public Boss(int hp, int damage) { "Новый Метод для хп босса и урона который он будет наносить"
        this.hp = hp;
        this.damage = damage;  "this чтобы код не путался между параметрами и методом в котором он находится"
    }

    public void takeDamage(int amount) { "метод для хп босса"
        hp = Math.max(0, hp - amount);  "выбирает 0 если герои снесут хп больше чем было у босса"
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
