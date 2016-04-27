import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Sebastian on 26/04/2016.
 */
public class GameStart {
    public static void main(String[] args) {
        Game game = new Game();

        //Gonna have hypothetical game history here for testing
        game.superMove("6343");
        game.botMove();
        game.superMove("4334");
        game.botMove();
        game.superMove("7333");
        game.botMove();
        game.superMove("6545");
        game.botMove();

        game.printBoard();
        System.out.println(processList(game.legalMoves(Piece.WHITE)));

        Scanner scan = new Scanner(System.in);

        while (scan.hasNext()) {
            String input = scan.nextLine();
            input = process(input, -1);
            LinkedList<String> currentLegalMoves = game.legalMoves(Piece.WHITE);
            if (existsIn(currentLegalMoves, input)) {
                game.superMove(input);
                game.botMove();
                game.printBoard();
                System.out.println(processList(game.legalMoves(Piece.WHITE)));
            } else {
                System.out.println("Not a legal move");
            }
        }
    }

    private static boolean existsIn(LinkedList<String> list, String input) {
        for (String string: list) {
            if (string.equals(input)) {
                return true;
            }
        }

        return false;
    }

    static String process(String input, int val) {
        String ret = "";
        for (int i = 0; i < input.length(); i++) {
            ret = ret + (Integer.parseInt(input.charAt(i) + "") + val);
        }

        return ret;
    }

    private static LinkedList<String> processList(LinkedList<String> list) {
        LinkedList<String> newlist = new LinkedList<>();

        for (String string: list) {
            newlist.add(process(string, 1));
        }

        return newlist;
    }
}
