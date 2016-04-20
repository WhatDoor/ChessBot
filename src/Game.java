/**
 * Created by Sebastian on 20/04/2016.
 */
public class Game {
    Piece[][] board;

    public Game() {
        board = new Piece[8][8];
    }

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println(game.board[1][1]);
    }
}
