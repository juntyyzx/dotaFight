public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        while (!game.isFinished()) {
            game.playRound();
        }
        game.printResult();
    }
}