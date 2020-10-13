package com.chess.engine.Moves;

import com.chess.engine.board.Board;

public class MoveTransition {
    private final Board fromBoard;
    private final Board toBoard;
    private final Move move;

    public MoveTransition(Board fromBoard, Board toBoard, Move move){
        this.fromBoard = fromBoard;
        this.toBoard = toBoard;
        this.move = move;
    }

    public Board getFromBoard() {
        return fromBoard;
    }

    public Board getToBoard() {
        return toBoard;
    }

    public Move getMove() {
        return move;
    }
}
