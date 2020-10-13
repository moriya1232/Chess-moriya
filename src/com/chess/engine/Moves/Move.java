package com.chess.engine.Moves;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.PieceType;

public abstract class Move {
    protected Piece piece;
    protected int destination;
    protected int sourceTileCoordinate;

    public Move(Piece piece, int destination) {
        this.piece = piece;
        this.destination = destination;
        this.sourceTileCoordinate = piece.getPosition();
    }

    public int getSourceTileCoordinate() {
        return sourceTileCoordinate;
    }

    public int getDestination() {
        return destination;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece p) {this.piece = p;}

    public abstract String getMoveWrittenWithoutStatus();

}
