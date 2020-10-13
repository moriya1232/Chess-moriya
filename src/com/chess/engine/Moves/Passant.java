package com.chess.engine.Moves;


import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;

public class Passant extends Move {
    public Passant(Pawn pawn, int destination) {
        super(pawn, destination);
    }

    @Override
    public String getMoveWrittenWithoutStatus() {
        return this.piece.getNamePosition().charAt(0) + "x" + BoardUtils.getNameTileByCoordinate.get(this.destination);
    }
    //TODO
}
