package com.chess.engine.Moves;

import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;

//הכתרה
public class Promotion extends Move {
    int destination;
    Piece pieceAfterCoronation;

    public Promotion(Piece pawn, int destination, Piece pieceAfterCoronation ) {
        super(pawn, destination);
        this.pieceAfterCoronation = pieceAfterCoronation;
    }

    @Override
    public String getMoveWrittenWithoutStatus() {
        return BoardUtils.getNameTileByCoordinate.get(destination) + "=" + this.pieceAfterCoronation.getPieceType().toString().charAt(0);
    }

    public Piece getPieceAfterCoronation() {
        return pieceAfterCoronation;
    }
}
