import java.util.LinkedList;

/**
 * Created by Sebastian on 20/04/2016.
 */
public class Game {
    Piece[][] board;
    Piece[][] boardCopy;
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
            Piece piece = new Piece(Piece.PAWN, Piece.WHITE);
            piece.setPawnFirstMove(true);
            board[1][i] = piece;
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
            Piece piece = new Piece(Piece.PAWN, Piece.BLACK);
            piece.setPawnFirstMove(true);
            board[6][i] = piece;
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
    
    public LinkedList<String> legalMoves(int colour) {
        LinkedList<String> moves = new LinkedList<>();

        if (colour == Piece.BLACK) {
            for (Piece piece: blackPieces) {
                int row = piece.getRow();
                int column = piece.getColumn();

                if (piece.getPieceType() == Piece.PAWN) {
                    potentialPawnMove(row, column, piece.isPawnFirstMove(), moves);

                } else if (piece.getPieceType() == Piece.ROOK) {
                    potentialRookMove(row, column, moves);
                }
            }
        }

        return moves;
    }

    private void potentialRookMove(int row, int column, LinkedList<String> moves) {
        int newRow = row;
        while (newRow - 1 >= 0) { //going up
            if (board[newRow - 1][column] == null || board[newRow - 1][column].getColour() == Piece.WHITE) {
                moves.add(newRow + "" + column + (newRow - 1) + column);
            } else if (board[newRow - 1][column].getColour() == Piece.BLACK) {
                break;
            }
            newRow++;
        }
    }

    private void potentialPawnMove(int row, int column, boolean firstMove, LinkedList<String> moves) {
        if (firstMove && board[row - 2][column] == null) { //going forwards 2 steps
            moves.add(row + "" + column + (row - 2) + column);

        }
        if (row - 1 >= 0 && board[row - 1][column] == null) { //Going forwards one step
            moves.add(row + "" + column + (row - 1) + column);

        }
        if (column - 1 >= 0 && board[row - 1][column - 1] != null && board[row - 1][column - 1].getColour() == Piece.WHITE) { //going up left
            moves.add(row + "" + column + (row - 1) + (column - 1));

        }
        if (column + 1 < 8 && board[row - 1][column + 1] != null && board[row - 1][column + 1].getColour() == Piece.WHITE) { //going up right
            moves.add(row + "" + column + (row - 1) + (column + 1));

        }
    }

    public void flipBoard() {
        Piece[][] tempboard = new Piece[8][8];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Piece piece = board[i][j];
                piece.setRow(7 - i);
                piece.setColumn(7 - j);
                tempboard[7 - i][7 - j] = piece;
            }
        }

        board = tempboard;
    }

    private void makeTempRecordOfBoard() {
        boardCopy = new Piece[8][8];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, boardCopy[i], 0, board[i].length);
        }
    }

    public void undo() {
        board = new Piece[8][8];
        for (int i = 0; i < boardCopy.length; i++) {
            System.arraycopy(boardCopy[i], 0, board[i], 0, boardCopy[i].length);
        }
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

    public boolean superMove(String move) {
        int fromRow = Integer.parseInt(move.charAt(0) + "");
        int fromColumn = Integer.parseInt(move.charAt(1) + "");
        int toRow = Integer.parseInt(move.charAt(2) + "");
        int toColumn = Integer.parseInt(move.charAt(3) + "");

        try {
            Piece piece = board[fromRow][fromColumn];
            piece.setRow(toRow);
            piece.setColumn(toColumn);
            if (piece.getPieceType() == Piece.PAWN) {
                piece.setPawnFirstMove(false);
            }
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
        game.superMove("6151");
        game.superMove("6222");
        game.makeTempRecordOfBoard();
        game.printBoard();
        LinkedList<String> list = game.legalMoves(Piece.BLACK);
        for (String s: list) {
            game.superMove(s);
            game.printBoard();
            game.undo();
        }
        list = game.legalMoves(Piece.BLACK);
        System.out.println(list);
    }
}
