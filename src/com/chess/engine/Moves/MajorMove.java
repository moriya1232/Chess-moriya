package com.chess.engine.Moves;

import com.chess.engine.pieces.Piece;

public abstract class MajorMove extends Move{

    public MajorMove(Piece piece, int destination) {
        super(piece,destination);
    }
}
