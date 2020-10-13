package ComputerMoves;

import Game.Status;
import Players.WhitePlayer;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.Alliance;
import com.chess.engine.pieces.Piece;

public class AppreciateBoard {

    public static double appreciate(Board board) {
        double result = 0;
        for (Piece piece: board.getWhitePieces()) {
            result +=piece.getValue();
        }
        for (Piece piece: board.getBlackPieces()) {
            result-=piece.getValue();
        }

        //checkmate
        if (board.getStatus() == Status.CHECKMATE && board.getTurn() == Alliance.WHITE) {
            result -=9999;
        } else if (board.getStatus() == Status.CHECKMATE && board.getTurn() == Alliance.BLACK) {
            result +=9999;
        }

        return result;
    }
}
