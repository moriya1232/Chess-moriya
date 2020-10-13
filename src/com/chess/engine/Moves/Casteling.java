package com.chess.engine.Moves;

import com.chess.engine.pieces.Alliance;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

//הצרחה
public class Casteling extends Move {

    public static int LEFT_ROOK_WHITE_POS = 56;
    public static int RIGHT_ROOK_WHITE_POS = 63;
    public static int LEFT_ROOK_BLACK_POS = 0;
    public static int RIGHT_ROOK_BLACK_POS = 7;
    private Rook rook;
    boolean isSmallCateling;
    private int destRook;

    public Casteling(Piece king, Piece rook) {
        super(king, king.getPosition());
        int destKing = 0;
        int destRook = 0;

        if (king.getAlliance() == Alliance.WHITE && rook.getPosition() == LEFT_ROOK_WHITE_POS) {
            isSmallCateling = false;
            destKing = piece.getPosition() - 2;
            destRook = rook.getPosition() + 3;
        } else if (piece.getAlliance() == Alliance.WHITE && rook.getPosition() == RIGHT_ROOK_WHITE_POS) {
            isSmallCateling = true;
            destKing = piece.getPosition() + 2;
            destRook = rook.getPosition() - 2;
        } else if (piece.getAlliance() == Alliance.BLACK && rook.getPosition() == LEFT_ROOK_BLACK_POS) {
            isSmallCateling = false;
            destKing = piece.getPosition() -2;
            destRook = rook.getPosition() + 3;
        } else if (piece.getAlliance() == Alliance.BLACK && rook.getPosition() == RIGHT_ROOK_BLACK_POS) {
            isSmallCateling = true;
            destKing = piece.getPosition() + 2;
            destRook = rook.getPosition() - 2;
        } else {
            System.out.println("error in casteling");
        }
        this.destination = destKing;
        this.rook = (Rook)rook;
        this.destRook = destRook;
    }

    public int getDestRook() {
        return destRook;
    }

    public Rook getRook() {
        return rook;
    }

    @Override
    public String getMoveWrittenWithoutStatus() {
        if (isSmallCateling) { return "0-0";}
        else { return "0-0-0";}
    }
}
