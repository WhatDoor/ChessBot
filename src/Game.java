import java.util.LinkedList;

/**
 * Created by Sebastian on 20/04/2016.
 */
public class Game {
    Piece[][] board;
    LinkedList<Piece> whitePieces;
    LinkedList<Piece> blackPieces;

    public Game() {
        generateNewBoard();
    }

    private void generateNewBoard() {
        board = new Piece[8][8];

        //White
        board[0][0] = new Piece(Piece.ROOK, Piece.WHITE);
        board[0][1] = new Piece(Piece.KNIGHT, Piece.WHITE);
        board[0][2] = new Piece(Piece.BISHOP, Piece.WHITE);
        board[0][3] = new Piece(Piece.KING, Piece.WHITE);
        board[0][4] = new Piece(Piece.QUEEN, Piece.WHITE);
        board[0][5] = new Piece(Piece.BISHOP, Piece.WHITE);
        board[0][6] = new Piece(Piece.KNIGHT, Piece.WHITE);
        board[0][7] = new Piece(Piece.ROOK, Piece.WHITE);

        for (int i = 0; i < board[1].length; i++) {
            board[1][i] = new Piece(Piece.PAWN, Piece.WHITE);
        }

        //Add white pieces to list, also records their location
        whitePieces = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Piece piece = board[i][j];
                piece.setRow(i);
                piece.setColumn(j);
                whitePieces.add(piece);
            }
        }

        //Black
        board[7][0] = new Piece(Piece.ROOK, Piece.BLACK);
        board[7][1] = new Piece(Piece.KNIGHT, Piece.BLACK);
        board[7][2] = new Piece(Piece.BISHOP, Piece.BLACK);
        board[7][3] = new Piece(Piece.KING, Piece.BLACK);
        board[7][4] = new Piece(Piece.QUEEN, Piece.BLACK);
        board[7][5] = new Piece(Piece.BISHOP, Piece.BLACK);
        board[7][6] = new Piece(Piece.KNIGHT, Piece.BLACK);
        board[7][7] = new Piece(Piece.ROOK, Piece.BLACK);

        for (int i = 0; i < board[6].length; i++) {
            board[6][i] = new Piece(Piece.PAWN, Piece.BLACK);
        }

        //Add black pieces to list, also records their location
        blackPieces = new LinkedList<>();
        for (int i = 6; i < 8; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Piece piece = board[i][j];
                piece.setRow(i);
                piece.setColumn(j);
                blackPieces.add(piece);
            }
        }
    }

    public void flipBoard() {
        Piece[][] tempboard = new Piece[8][8];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                tempboard[7 - i][7 - j] = board[i][j];
            }
        }

        board = tempboard;
    }

    public void printBoard() {
        for (Piece[] row: board) {
            for (Piece piece: row) {
                if (piece != null)
                    System.out.print(piece.toString() + "|");
                else
                    System.out.print("-1" + "|");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int evaluateBoard(int colour) {
        int score = 0;

        if (colour == Piece.BLACK) {
            for (Piece piece: blackPieces) {
                score += pieceScore(piece);
            }
        } else {
            for (Piece piece: whitePieces) {
                score += pieceScore(piece);
            }
        }

        return score;
    }

    private int pieceScore(Piece piece) {
        int score = 0;

        if (piece.getPieceType() == Piece.PAWN) {
            score += 1;
        } else if (piece.getPieceType() == Piece.KNIGHT) {
            score += 3;
        } else if (piece.getPieceType() == Piece.BISHOP) {
            score += 3;
        } else if (piece.getPieceType() == Piece.ROOK) {
            score += 5;
        } else if (piece.getPieceType() == Piece.QUEEN) {
            score += 9;
        }

        int[][] scoreSheet =
                {{1,1,1,1,1,1,1,1}
                ,{2,2,2,2,2,2,2,2}
                ,{2,2,3,3,3,3,2,2}
                ,{2,2,3,4,4,3,2,2}
                ,{2,2,3,4,4,3,2,2}
                ,{2,2,3,3,3,3,2,2}
                ,{2,2,2,2,2,2,2,2}
                ,{1,1,1,1,1,1,1,1}
        };

        score += scoreSheet[piece.getRow()][piece.getColumn()];

        return score;
    }

    public boolean superMove(int fromRow, int fromColumn, int toRow, int toColumn) {
        try {
            Piece piece = board[fromRow][fromColumn];
            piece.setRow(toRow);
            piece.setColumn(toColumn);
            board[fromRow][fromColumn] = null;

            board[toRow][toColumn] = piece;
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("That's not a real square");
            return false;
        }
    }



    public static void main(String[] args) {
        Game game = new Game();
        game.superMove(6,1,5,1);
        game.superMove(6,2,4,2);
        game.printBoard();
        System.out.println(game.evaluateBoard(Piece.BLACK));
        System.out.println(game.evaluateBoard(Piece.WHITE));
    }
}
