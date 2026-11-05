public class Game {
    int hp;
    int damage;
    String defenceType;
    int takeDamage(){
    while (hp > 0) {
        hp -= takeDamage();

    }
    }
}
