import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Sebastian on 20/04/2016.
 */
public class Game {
    Piece[][] board;
    Piece[][] boardCopy;
    LinkedList<Piece> whitePieces;
    LinkedList<Piece> blackPieces;
    LinkedList<Piece> blackPiecesCopy;

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
                    potentialPawnMoves(row, column, piece.isPawnFirstMove(), moves);

                } else if (piece.getPieceType() == Piece.ROOK) {
                    potentialRookMoves(row, column, moves);

                } else if (piece.getPieceType() == Piece.BISHOP) {
                    potentialBishopMoves(row, column, moves);

                } else if (piece.getPieceType() == Piece.QUEEN) { //The Queen basically just does both those things
                    potentialBishopMoves(row, column, moves);
                    potentialRookMoves(row, column, moves);

                } else if (piece.getPieceType() == Piece.KNIGHT) {
                    potentialKnightMoves(row, column, moves);

                } else if (piece.getPieceType() == Piece.KING) {
                    potentialKingMoves(row, column, moves);
                }
            }
        }

        return moves;
    }

    private void potentialKnightMoves(int row, int column, LinkedList<String> moves) {
        int newRow = row - 2;
        int newColumn = column - 1;
        if (newRow >= 0 && newColumn >= 0) { //up L left
            if (board[newRow][newColumn] == null || board[newRow][newColumn].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + newRow + newColumn);
            }
        }

        newRow = row - 2;
        newColumn = column + 1;
        if (newRow >= 0 && newColumn < 8) { //up L right
            if (board[newRow][newColumn] == null || board[newRow][newColumn].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + newRow + newColumn);
            }
        }

        newRow = row - 1;
        newColumn = column + 2;
        if (newRow >= 0 && newColumn < 8) { //right L up
            if (board[newRow][newColumn] == null || board[newRow][newColumn].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + newRow + newColumn);
            }
        }

        newRow = row + 1;
        newColumn = column + 2;
        if (newRow < 8 && newColumn < 8) { //right L down
            if (board[newRow][newColumn] == null || board[newRow][newColumn].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + newRow + newColumn);
            }
        }

        newRow = row + 2;
        newColumn = column - 1;
        if (newRow < 8 && newColumn >= 0) { //down L left
            if (board[newRow][newColumn] == null || board[newRow][newColumn].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + newRow + newColumn);
            }
        }

        newRow = row + 2;
        newColumn = column + 1;
        if (newRow < 8 && newColumn < 8) { //down L right
            if (board[newRow][newColumn] == null || board[newRow][newColumn].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + newRow + newColumn);
            }
        }

        newRow = row - 1;
        newColumn = column - 2;
        if (newRow >= 0 && newColumn >= 0) { //left L up
            if (board[newRow][newColumn] == null || board[newRow][newColumn].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + newRow + newColumn);
            }
        }

        newRow = row + 1;
        newColumn = column - 2;
        if (newRow < 8 && newColumn >= 0) { //left L down
            if (board[newRow][newColumn] == null || board[newRow][newColumn].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + newRow + newColumn);
            }
        }


    }

    private void potentialKingMoves(int row, int column, LinkedList<String> moves) {
        if (row - 1 >= 0 && (board[row - 1][column] == null || board[row - 1][column].getColour() == Piece.WHITE)) { //up
            moves.add(row + "" + column + (row - 1) + column);
        }

        if (row + 1 < 8 && (board[row + 1][column] == null || board[row + 1][column].getColour() == Piece.WHITE)) { //down
            moves.add(row + "" + column + (row + 1) + column);
        }

        if (column - 1 >= 0 && (board[row][column - 1] == null || board[row][column - 1].getColour() == Piece.WHITE)) { //left
            moves.add(row + "" + column + row + (column - 1));
        }

        if (column + 1 < 8 && (board[row][column + 1] == null || board[row][column + 1].getColour() == Piece.WHITE)) { //right
            moves.add(row + "" + column + row + (column + 1));
        }

        if (row - 1 >= 0 && column + 1 < 8 && (board[row - 1][column + 1] == null || board[row - 1][column + 1].getColour() == Piece.WHITE)) { //up right
            moves.add(row + "" + column + (row - 1) + (column + 1));
        }

        if (row + 1 < 8 && column + 1 < 8 && (board[row + 1][column + 1] == null || board[row + 1][column + 1].getColour() == Piece.WHITE)) { //down right
            moves.add(row + "" + column + (row + 1) + (column + 1));
        }

        if (row + 1 < 8 && column - 1 >= 0 && (board[row + 1][column - 1] == null || board[row + 1][column - 1].getColour() == Piece.WHITE)) { //down left
            moves.add(row + "" + column + (row + 1) + (column - 1));
        }

        if (row - 1 >= 0 && column - 1 >= 0 && (board[row - 1][column + 1] == null || board[row - 1][column + 1].getColour() == Piece.WHITE)) { //up left
            moves.add(row + "" + column + (row - 1) + (column - 1));
        }
    }

    private void potentialBishopMoves(int row, int column, LinkedList<String> moves) {
        int newRow = row - 1;
        int newColumn = column - 1;
        while (newRow >= 0 && newColumn >= 0) { //Going up left
            if (board[newRow][newColumn] == null) {
                moves.add(row + "" + column + newRow + newColumn);
            } else if (board[newRow][newColumn].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + newRow + newColumn);
                break;
            } else if (board[newRow][newColumn].getColour() == Piece.BLACK) {
                break;
            }
            newRow--;
            newColumn--;
        }

        newRow = row - 1;
        newColumn = column + 1;
        while (newRow >= 0 && newColumn < 8) { //Going up right
            if (board[newRow][newColumn] == null) {
                moves.add(row + "" + column + newRow + newColumn);
            } else if (board[newRow][newColumn].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + newRow + newColumn);
                break;
            } else if (board[newRow][newColumn].getColour() == Piece.BLACK) {
                break;
            }
            newRow--;
            newColumn++;
        }

        newRow = row + 1;
        newColumn = column - 1;
        while (newRow < 8 && newColumn >= 0) { //Going down left
            if (board[newRow][newColumn] == null) {
                moves.add(row + "" + column + newRow + newColumn);
            } else if (board[newRow][newColumn].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + newRow + newColumn);
                break;
            } else if (board[newRow][newColumn].getColour() == Piece.BLACK) {
                break;
            }
            newRow++;
            newColumn--;
        }

        newRow = row + 1;
        newColumn = column + 1;
        while (newRow < 8 && newColumn < 8) { //Going down right
            if (board[newRow][newColumn] == null) {
                moves.add(row + "" + column + newRow + newColumn);
            } else if (board[newRow][newColumn].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + newRow + newColumn);
                break;
            } else if (board[newRow][newColumn].getColour() == Piece.BLACK) {
                break;
            }
            newRow++;
            newColumn++;
        }

    }

    private void potentialRookMoves(int row, int column, LinkedList<String> moves) {
        int newRow = row - 1;
        while (newRow >= 0) { //going up
            if (board[newRow][column] == null) {
                moves.add(row + "" + column + newRow + column);
            } else if (board[newRow][column].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + newRow + column);
                break;
            } else if (board[newRow][column].getColour() == Piece.BLACK) {
                break;
            }
            newRow--;
        }

        newRow = row + 1;
        while (newRow < 8) { //going down
            if (board[newRow][column] == null) {
                moves.add(row + "" + column + newRow + column);
            } else if (board[newRow][column].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + newRow + column);
                break;
            }else if (board[newRow][column].getColour() == Piece.BLACK) {
                break;
            }
            newRow++;
        }

        int newColumn = column + 1;
        while (newColumn < 8) { //going right
            if (board[row][newColumn] == null) {
                moves.add(row + "" + column + row + newColumn);
            } else if (board[row][newColumn].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + row + newColumn);
                break;
            } else if (board[row][newColumn].getColour() == Piece.BLACK) {
                break;
            }
            newColumn++;
        }

        newColumn = column - 1;
        while (newColumn >= 0) { //going left
            if (board[row][newColumn] == null) {
                moves.add(row + "" + column + row + newColumn);
            } else if (board[row][newColumn].getColour() == Piece.WHITE) {
                moves.add(row + "" + column + row + newColumn);
                break;
            } else if (board[row][newColumn].getColour() == Piece.BLACK) {
                break;
            }
            newColumn--;
        }
    }

    private void potentialPawnMoves(int row, int column, boolean firstMove, LinkedList<String> moves) {
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
                if (piece != null) {
                    piece.setRow(7 - i);
                    piece.setColumn(7 - j);
                }
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
            for (Piece piece: whitePieces) {
                score -= pieceScore(piece);
            }

            score = score + scoreSheet(Piece.BLACK) - scoreSheet(Piece.WHITE);

        } else {
            for (Piece piece: whitePieces) {
                score += pieceScore(piece);
            }
            for (Piece piece: blackPieces) {
                score -= pieceScore(piece);
            }

            score = score + scoreSheet(Piece.WHITE) - scoreSheet(Piece.BLACK);
        }

        return score;
    }

    private int scoreSheet(int colour) {
        int score = 0;

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

        if (colour == Piece.BLACK) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] != null && board[i][j].getColour() == Piece.BLACK){
                        score += scoreSheet[i][j];
                    }
                }
            }
        } else {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] != null && board[i][j].getColour() == Piece.WHITE){
                        score += scoreSheet[i][j];
                    }
                }
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

    public boolean testMove(String move) {
        int fromRow = Integer.parseInt(move.charAt(0) + "");
        int fromColumn = Integer.parseInt(move.charAt(1) + "");
        int toRow = Integer.parseInt(move.charAt(2) + "");
        int toColumn = Integer.parseInt(move.charAt(3) + "");

        try {
            Piece piece = board[fromRow][fromColumn];
            board[fromRow][fromColumn] = null;
            board[toRow][toColumn] = piece;
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("That's not a real square");
            return false;
        }
    }

    public boolean userMove(String move) {
        int fromRow = Integer.parseInt(move.charAt(0) + "") - 1;
        int fromColumn = Integer.parseInt(move.charAt(1) + "") - 1;
        int toRow = Integer.parseInt(move.charAt(2) + "") - 1;
        int toColumn = Integer.parseInt(move.charAt(3) + "") - 1;

        if (fromRow < 0) {
            fromRow = 0;
        }

        if (fromColumn < 0) {
            fromColumn = 0;
        }

        if (toRow < 0) {
            toRow = 0;
        }

        if (toColumn < 0) {
            toColumn = 0;
        }

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

    public String bestMove(LinkedList<String> moves) {
        int bestScore = -1;
        String bestMove = "";

        makeTempRecordOfBoard();
        for (String move: moves) {
            testMove(move);
            int score = evaluateBoard(Piece.BLACK);
            System.out.print(score + ", ");

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
            undo();
        }
        System.out.println();

        return bestMove;
    }



    public static void main(String[] args) {
        Game game = new Game();
        game.flipBoard();
        game.printBoard();
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String in = scan.nextLine();
            game.userMove(in);
            game.printBoard();
            game.flipBoard();
            LinkedList<String> legalMoves = game.legalMoves(Piece.BLACK);
            System.out.println(legalMoves);
            String move = game.bestMove(legalMoves);
            System.out.println(move);
            game.superMove(move);
            game.flipBoard();
            game.printBoard();
        }
    }
}
