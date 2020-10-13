package com.chess.engine.Moves;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;
import java.util.Collection;

public class MoveFactory {

    public MoveFactory(){}

    public Move createMove(Board board, Piece p, int dest) {
        if (p == null) {return null;}
        Collection<Move> allLegalMoves = board.getAllLegalMovesOfColor(p.getAlliance());
        for (Move m: allLegalMoves) {
            if (m.getPiece() == p && m.getDestination() == dest) {
//                if (m instanceof Coronation) { TODO
//                    if ( =((Coronation) m).getPieceAfterCoronation();
//                } else if (m instanceof  AttackCoronation) {
//                    p =((AttackCoronation) m).getPieceAfterCoronation();
//                }
                return m;
            }
        }
        return null;
    }
}
