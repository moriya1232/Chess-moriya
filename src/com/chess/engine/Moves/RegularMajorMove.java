package com.chess.engine.Moves;

import com.chess.engine.Moves.MajorMove;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.PieceType;

public class RegularMajorMove extends MajorMove {

    public RegularMajorMove(Piece piece, int destination) {
        super(piece, destination);
    }

    @Override
    public String getMoveWrittenWithoutStatus() {
        String dest = BoardUtils.getNameTileByCoordinate.get(this.destination);
        String pieceLetter = "";
        if (this.piece.getPieceType()!= PieceType.PAWN) {
            char pieceLetterChar = piece.getPieceType().toString().charAt(0);
            pieceLetter+=pieceLetterChar;
        }
        return pieceLetter + dest;
    }
}
