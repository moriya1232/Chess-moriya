package com.chess.engine.Moves;

import com.chess.engine.Moves.MajorMove;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.PieceType;

public class AttackMajorMove extends MajorMove {
    protected Piece attackedpiece;

    public AttackMajorMove(Piece piece, int destination, Piece attackedpiece) {
        super(piece, destination);
        this.attackedpiece=attackedpiece;
    }

    @Override
    public String getMoveWrittenWithoutStatus() {
        String dest = BoardUtils.getNameTileByCoordinate.get(this.destination);
        String pieceLetter = "";
        char pieceLetterChar;
        if (this.piece.getPieceType()!= PieceType.PAWN) {
            pieceLetter += piece.getPieceType().toString().charAt(0);
        } else {
            pieceLetter += piece.getNamePosition().toString().charAt(0);
        }
        return pieceLetter + "x" +dest;

    }
}
