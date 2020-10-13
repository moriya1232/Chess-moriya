package com.chess.engine.Moves;

import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;

public class AttackPromotion extends Move{
    protected Piece pieceAfterCoronation;

    public AttackPromotion(Pawn pawn, int destination, Piece pieceAfterCoronation) {
        super(pawn, destination);
        this.pieceAfterCoronation = pieceAfterCoronation;
    }

    @Override
    public String getMoveWrittenWithoutStatus() {
        return this.piece.getNamePosition().charAt(0)+ "x" + BoardUtils.getNameTileByCoordinate.get(destination) + "=" + this.pieceAfterCoronation.getPieceType().toString().charAt(0);
    }

    public Piece getPieceAfterCoronation() {
        return pieceAfterCoronation;
    }
}
