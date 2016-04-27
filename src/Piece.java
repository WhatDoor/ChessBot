/**
 * Created by Sebastian on 20/04/2016.
 */
public class Piece {
    final static int PAWN = 1;
    final static int ROOK = 2;
    final static int KNIGHT = 3;
    final static int BISHOP = 4;
    final static int QUEEN = 5;
    final static int KING = 6;

    final static int BLACK = 0;
    final static int WHITE = 1;

    private int pieceType = -1;
    private int colour = -1;

    private int row = -1;
    private int column = -1;

    //Special Flags
    private boolean pawnFirstMove = false;

    public Piece(int pieceType, int colour) {
        this.pieceType = pieceType;
        this.colour = colour;
    }

    public int getPieceType() {
        return pieceType;
    }

    public int getColour() {
        return colour;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isPawnFirstMove() {
        return pawnFirstMove;
    }

    public void setPawnFirstMove(boolean pawnFirstMove) {
        this.pawnFirstMove = pawnFirstMove;
    }

    public String printCharacter() {
        String ret = "";
        if (colour == BLACK) {
            ret += "B";
        } else {
            ret += "W";
        }

        if (pieceType == PAWN) {
            ret += "P";
        } else if (pieceType == ROOK) {
            ret += "R";
        } else if (pieceType == KNIGHT) {
            ret += "N";
        } else if (pieceType == BISHOP) {
            ret += "B";
        } else if (pieceType == QUEEN) {
            ret += "Q";
        } else {
            ret += "K";
        }

        return ret;
    }

    public String toString() {
        String ret = printCharacter();
        ret = ret + "- " + (row + 1) + (column + 1);

        return ret;
    }
}
